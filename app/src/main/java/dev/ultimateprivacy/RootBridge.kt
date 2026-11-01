package dev.ultimateprivacy

import java.io.BufferedReader
import java.io.InputStreamReader

object RootBridge {
    private val allowed = setOf("status", "sensor enable", "sensor disable", "lockdown on", "lockdown off")

    fun run(command: String): String {
        if (command !in allowed) return "Command rejected."
        return try {
            val p = ProcessBuilder("su", "-c", "/data/adb/modules/ultimate_privacy/action.sh $command")
                .redirectErrorStream(true).start()
            val output = BufferedReader(InputStreamReader(p.inputStream)).use { it.readText() }
            "exit=${p.waitFor()}\n$output"
        } catch (e: Exception) {
            "Root/module unavailable: ${e.message}"
        }
    }
}
