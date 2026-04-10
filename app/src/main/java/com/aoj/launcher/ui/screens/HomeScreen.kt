package com.aoj.launcher.ui.screens
        Column {
            Text(
                text = "APP INVENTORY",
                color = TacticalKhaki,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$appCount launchable apps detected",
                color = TacticalMutedText,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = onRefresh,
            colors = ButtonDefaults.buttonColors(
                containerColor = TacticalKhaki,
                contentColor = androidx.compose.ui.graphics.Color.Black
            )
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            Text(text = " REFRESH")
        }
    }
}

@Composable
private fun InfoPanel(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(8.dp))
            .background(TacticalPanel, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = TacticalKhaki,
            style = androidx.compose.material3.MaterialTheme.typography.titleMedium
        )
        Text(
            text = body,
            color = TacticalText,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )
    }
}
