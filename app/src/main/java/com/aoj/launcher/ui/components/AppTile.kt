package com.aoj.launcher.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aoj.launcher.model.AppInfo
import com.aoj.launcher.ui.theme.*

@Composable
fun AppTile(
    app: AppInfo,
    isPinned: Boolean,
    onClick: () -> Unit,
    onTogglePinned: () -> Unit,
    onToggleHidden: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(8.dp))
            .background(TacticalPanelSoft, RoundedCornerShape(8.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = { onTogglePinned() },
                onDoubleClick = { onToggleHidden() }
            )
}
