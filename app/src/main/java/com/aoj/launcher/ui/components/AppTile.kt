package com.aoj.launcher.ui.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import com.aoj.launcher.model.AppInfo
import com.aoj.launcher.ui.theme.TacticalLine
import com.aoj.launcher.ui.theme.TacticalPanelSoft
import com.aoj.launcher.ui.theme.TacticalText

@Composable
fun AppTile(
    app: AppInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(8.dp))
            .background(TacticalPanelSoft)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DrawableIcon(
            drawable = app.icon,
            contentDescription = app.label,
            modifier = Modifier.size(44.dp)
        )

        Text(
            text = app.label,
            color = TacticalText,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = androidx.compose.material3.MaterialTheme.typography.labelMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
