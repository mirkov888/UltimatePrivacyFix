# Ultimate Privacy v0.2.0 — Maximum mode

Targeted build profile: Redmi Note 12 Pro 4G (`sweet`), Android 16, KernelSU-Next.
This package is a conservative source/module update. It is **not** tested on the user's device.

## Scope / limits
- Exposes explicit sensor privacy shell request only if `cmd sensor_privacy` exists on this ROM. Android OEM shell command variants differ; check status and verify the Settings privacy toggles manually.
- Does not spoof or alter IMEI. IMEI is a modem/carrier identifier and should not be manipulated.
- Does not force MAC changes with `ip link`, driver hacks, or Wi-Fi disconnect loops. Android's MAC randomization is configured per saved network and its rotation behavior is controlled by Android/OEM.
- `lockdown` is an audit marker, not a hardware sensor kill switch. Use Settings > Privacy > Camera access / Microphone access for the verified global toggles.
- No background automation, radio toggles, app-op blanket revocation, or hidden persistent daemon is installed.

## Install
KernelSU Manager → Modules → Install from storage → select `ultimate-privacy-kSU-v0.2.0.zip` → reboot.
Do not install from custom recovery; KernelSU module ZIP install is through its manager.

## Commands
```sh
su -c /data/adb/modules/ultimate_privacy/action.sh status
su -c /data/adb/modules/ultimate_privacy/action.sh sensor enable
su -c /data/adb/modules/ultimate_privacy/action.sh sensor disable
su -c /data/adb/modules/ultimate_privacy/action.sh lockdown on
su -c /data/adb/modules/ultimate_privacy/action.sh lockdown off
```

## MAC randomized per network
On Android: Settings → Wi-Fi → tap current/saved network → Privacy/MAC address type → Use randomized MAC (wording may vary). Android 10+ generally enables randomized MAC by default; persistent vs non-persistent rotation is framework/OEM controlled. Verify after disconnect/reconnect; do not assume timed rotation is supported.

## Tests
1. Install, reboot, check `status`.
2. Try sensor command; check Settings > Privacy > Camera and Microphone access, and test a camera/mic app.
3. Ensure phone calls, emergency calls, Wi-Fi, Bluetooth and mobile data still work.
4. Set randomized MAC for a test Wi-Fi, reconnect, inspect network details/router lease; avoid changing MAC while connected.
5. Verify identifier permissions under Settings > Apps > Permissions / Special app access. Ordinary apps generally cannot read IMEI without privileged permission; this module does not promise universal spoofing.
6. If any unexpected behavior, disable module in KernelSU Manager and reboot.

## Source
Android app uses Kotlin + Compose and a strict allow-list root bridge. Open the app project in Android Studio, JDK 17, then build APK. An APK is not bundled here.
