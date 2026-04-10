package com.aoj.launcher.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aoj.launcher.ui.theme.TacticalKhaki
import com.aoj.launcher.ui.theme.TacticalLine
import com.aoj.launcher.ui.theme.TacticalPanel
import com.aoj.launcher.ui.theme.TacticalText

@Composable
fun SettingsPanel(
    adminMode: Boolean,
    showWatermark: Boolean,
    showBackgroundImage: Boolean,
    onSetAdminMode: (Boolean) -> Unit,
    onSetWatermark: (Boolean) -> Unit,
    onSetBackgroundImage: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(8.dp))
            .background(TacticalPanel, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "SETTINGS",
            color = TacticalKhaki,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )

        SettingRow(
            label = "Admin mode",
            checked = adminMode,
            onCheckedChange = onSetAdminMode
        )

        SettingRow(
            label = "Background image",
            checked = showBackgroundImage,
            onCheckedChange = onSetBackgroundImage
        )

        SettingRow(
            label = "Logo watermark",
            checked = showWatermark,
            onCheckedChange = onSetWatermark
        )
    }
}

@Composable
private fun SettingRow(
}
