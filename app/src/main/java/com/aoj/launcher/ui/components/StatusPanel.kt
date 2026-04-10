package com.aoj.launcher.ui.components

import android.content.Context
import android.net.ConnectivityManager
import android.os.BatteryManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aoj.launcher.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun StatusPanel() {
    val context = LocalContext.current
    var battery by remember { mutableStateOf(0) }
    var wifi by remember { mutableStateOf("UNKNOWN") }

    LaunchedEffect(Unit) {
        while (true) {
            battery = getBatteryLevel(context)
            wifi = getWifiState(context)
            delay(5000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, TacticalLine)
            .background(TacticalPanel)
            .padding(10.dp)
    ) {
        Text("STATUS", color = TacticalKhaki)
        Text("Battery: $battery%", color = TacticalText)
        Text("WiFi: $wifi", color = TacticalText)
    }
}

private fun getBatteryLevel(context: Context): Int {
    val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
}

private fun getWifiState(context: Context): String {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetworkInfo
    return if (network != null && network.isConnected) "CONNECTED" else "OFFLINE"
}
