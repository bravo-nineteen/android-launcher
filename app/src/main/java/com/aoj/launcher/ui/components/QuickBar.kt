package com.aoj.launcher.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aoj.launcher.ui.theme.*

@Composable
fun QuickBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, TacticalLine)
            .background(TacticalPanel)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        QuickButton("AOJ")
        QuickButton("EVENTS")
        QuickButton("TOOLS")
        QuickButton("MAP")
    }
}

@Composable
private fun QuickButton(label: String) {
    Box(
        modifier = Modifier
            .border(1.dp, TacticalLine)
            .padding(8.dp)
    ) {
        Text(label, color = TacticalText)
    }
}
