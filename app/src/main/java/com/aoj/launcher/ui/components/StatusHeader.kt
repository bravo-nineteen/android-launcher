package com.aoj.launcher.ui.components
fun StatusHeader(
    isCompact: Boolean,
    modifier: Modifier = Modifier
) {
    var now by remember { mutableStateOf(Date()) }

    LaunchedEffect(Unit) {
        while (true) {
            now = Date()
            delay(1000)
        }
    }

    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(10.dp))
            .background(TacticalPanel, RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "AOJ OPERATIONAL LAUNCHER",
                color = TacticalKhaki,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (isCompact) "COMPACT DEVICE PROFILE" else "TABLET FIELD TERMINAL PROFILE",
                color = TacticalMutedText,
                style = androidx.compose.material3.MaterialTheme.typography.labelMedium
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = timeFormat.format(now),
                color = TacticalText,
                style = if (isCompact) {
                    androidx.compose.material3.MaterialTheme.typography.headlineMedium
                } else {
                    androidx.compose.material3.MaterialTheme.typography.headlineLarge
                }
            )
            Text(
                text = dateFormat.format(now).uppercase(Locale.getDefault()),
                color = TacticalMutedText,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }
    }
}
