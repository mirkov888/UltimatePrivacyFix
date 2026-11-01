#!/system/bin/sh
BASE=/data/adb/ultimate-privacy
mkdir -p "$BASE"
LOG="$BASE/actions.log"
log() { echo "$(date '+%F %T' 2>/dev/null) $*" >> "$LOG"; }

# Sensor privacy command syntax differs across Android/OEM builds.
# Only invoke after checking that this build exposes the command.
sensor_cmd() {
  if ! cmd sensor_privacy help >/dev/null 2>&1; then
    echo "Sensor privacy shell service unavailable on this ROM." >&2
    return 1
  fi
  cmd sensor_privacy "$@"
}

case "$1" in
  status)
    echo "Module: $( [ -d /data/adb/modules/ultimate_privacy ] && echo installed || echo missing )"
    echo "Camera/mic: use Android Privacy dashboard to verify; OEM shell service varies."
    echo "MAC: Android per-network randomization; no unsupported driver changes performed."
    echo "Identifier: no IMEI spoofing; telephony identity remains managed by Android/modem."
    ;;
  sensor)
    case "$2" in
      enable)
        echo "Enabling global sensor privacy requires supported Android shell syntax; use Settings > Privacy > Camera/Microphone access if command unsupported."
        sensor_cmd enable || exit $?
        log "sensor privacy enable requested"
        ;;
      disable)
        sensor_cmd disable || exit $?
        log "sensor privacy disable requested"
        ;;
      *) echo "Usage: $0 sensor enable|disable" >&2; exit 2;;
    esac
    ;;
  lockdown)
    case "$2" in
      on)
        echo "1" > "$BASE/lockdown"
        echo "Lockdown marker enabled. Confirm Camera and Microphone access toggles in Android Settings."
        log "lockdown marker on"
        ;;
      off)
        rm -f "$BASE/lockdown"
        echo "Lockdown marker disabled."
        log "lockdown marker off"
        ;;
      *) echo "Usage: $0 lockdown on|off" >&2; exit 2;;
    esac
    ;;
  *)
    echo "Usage: $0 {status|sensor enable\|disable|lockdown on\|off}" >&2
    exit 2
    ;;
esac
