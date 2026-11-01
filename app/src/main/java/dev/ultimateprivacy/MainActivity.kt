package dev.ultimateprivacy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme()) {
                MaximumPrivacyScreen()
            }
        }
    }
}

@Composable
private fun MaximumPrivacyScreen() {
    var output by remember { mutableStateOf("Press Status to inspect module.") }
    val scope = rememberCoroutineScope()
    fun command(value: String) {
        scope.launch { output = withContext(Dispatchers.IO) { RootBridge.run(value) } }
    }
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Ultimate Privacy", style = MaterialTheme.typography.headlineMedium)
            Text("Maximum • Android 16 • KernelSU-Next")
            Text("Sensor controls", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { command("sensor enable") }, modifier = Modifier.fillMaxWidth()) {
                Text("Request sensor privacy ON")
            }
            OutlinedButton(onClick = { command("sensor disable") }, modifier = Modifier.fillMaxWidth()) {
                Text("Request sensor privacy OFF")
            }
            Text("Lockdown", style = MaterialTheme.typography.titleLarge)
            Text("Marker only; use Android Settings > Privacy to confirm global Camera and Microphone access switches.")
            Button(onClick = { command("lockdown on") }, modifier = Modifier.fillMaxWidth()) { Text("Enable Lockdown marker") }
            OutlinedButton(onClick = { command("lockdown off") }, modifier = Modifier.fillMaxWidth()) { Text("Disable Lockdown marker") }
            Text("Identifiers & Wi-Fi MAC", style = MaterialTheme.typography.titleLarge)
            Text("No IMEI spoofing is performed. Enable randomized MAC per network in Wi-Fi network details. Rotation frequency is controlled by Android/OEM, not this app.")
            OutlinedButton(onClick = { command("status") }, modifier = Modifier.fillMaxWidth()) { Text("Check status") }
            Text(output)
        }
    }
}
