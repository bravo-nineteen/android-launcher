package com.aoj.launcher.ui.screens

import android.content.Intent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class LauncherApp(
    val label: String,
    val packageName: String,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    onLaunchApp: (String) -> Unit
) {
    val context = LocalContext.current
    val pm = context.packageManager

    var apps by remember { mutableStateOf<List<LauncherApp>>(emptyList()) }
    var selectedApp by remember { mutableStateOf<LauncherApp?>(null) }

    LaunchedEffect(Unit) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolved = pm.queryIntentActivities(intent, 0)

        val loadedApps = resolved
            .mapNotNull { resolveInfo ->
                val label = resolveInfo.loadLabel(pm).toString().trim()
                val packageName = resolveInfo.activityInfo?.packageName?.trim().orEmpty()

                if (label.isBlank() || packageName.isBlank() || packageName == context.packageName) {
                    null
                } else {
                    LauncherApp(
                        label = label,
                        packageName = packageName,
                        icon = iconForLabel(label)
                    )
                }
            }
            .distinctBy { it.packageName }
            .sortedBy { it.label.lowercase() }

        apps = loadedApps
        if (loadedApps.isNotEmpty()) {
            selectedApp = loadedApps.first()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF08110D))
    ) {
        TacticalBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            TopStatusBar()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SideRail(
                    apps = apps.take(6),
                    selectedPackage = selectedApp?.packageName,
                    onSelect = { app ->
                        selectedApp = app
                        onLaunchApp(app.packageName)
                    }
                )

                MainPanel(
                    selectedApp = selectedApp,
                    apps = apps,
                    onSelectApp = { app ->
                        selectedApp = app
                    },
                    onLaunchApp = { app ->
                        selectedApp = app
                        onLaunchApp(app.packageName)
                    }
                )
            }
        }
    }
}

@Composable
private fun TacticalBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1511),
                        Color(0xFF08110D),
                        Color(0xFF050907)
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridStep = 60f
            val lineColor = Color(0x2239FF9C)

            var x = 0f
            while (x <= size.width) {
                drawLine(
                    color = lineColor,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 1f
                )
                x += gridStep
            }

            var y = 0f
            while (y <= size.height) {
                drawLine(
                    color = lineColor,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1f
                )
                y += gridStep
            }

            drawLine(
                color = Color(0x3350C878),
                start = Offset(size.width * 0.22f, 0f),
                end = Offset(size.width * 0.65f, size.height),
                strokeWidth = 2f
            )

            drawLine(
                color = Color(0x22C8A050),
                start = Offset(size.width * 0.72f, 0f),
                end = Offset(size.width, size.height * 0.38f),
                strokeWidth = 2f
            )
        }
    }
}

@Composable
private fun TopStatusBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF274338), RoundedCornerShape(16.dp))
            .background(Color(0xCC0C1712), RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "AOJ FIELD TERMINAL",
                color = Color(0xFFE4E7DE),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "DIGITAL TACTICAL SCREEN",
                color = Color(0xFF86A28D),
                fontSize = 11.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            StatusChip("SYS OK", Icons.Default.Storage)
            StatusChip("WIFI", Icons.Default.Wifi)
            StatusChip("CONFIG", Icons.Default.Settings)
        }
    }
}

@Composable
private fun StatusChip(
    text: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .border(1.dp, Color(0xFF2D4A3D), RoundedCornerShape(12.dp))
            .background(Color(0xB30B140F), RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color(0xFFB7BE8A),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            color = Color(0xFFD7DBCF),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SideRail(
    apps: List<LauncherApp>,
    selectedPackage: String?,
    onSelect: (LauncherApp) -> Unit
) {
    Column(
        modifier = Modifier
            .width(118.dp)
            .fillMaxHeight()
            .border(1.dp, Color(0xFF274338), RoundedCornerShape(18.dp))
            .background(Color(0xB30A1410), RoundedCornerShape(18.dp))
            .padding(vertical = 12.dp, horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        apps.forEach { app ->
            SideRailButton(
                app = app,
                selected = app.packageName == selectedPackage,
                onClick = { onSelect(app) }
            )
        }
    }
}

@Composable
private fun SideRailButton(
    app: LauncherApp,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFF7DB18C) else Color(0xFF263C32)
    val backgroundColor = if (selected) Color(0xCC17261E) else Color(0x99101814)
    val iconTint = if (selected) Color(0xFFE5C07B) else Color(0xFF8EA2D9)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .background(backgroundColor, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .border(1.dp, borderColor.copy(alpha = 0.8f), RoundedCornerShape(26.dp))
                .background(Color(0x22000000), RoundedCornerShape(26.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = app.icon,
                contentDescription = app.label,
                tint = iconTint,
                modifier = Modifier.size(26.dp)
            )
        }

        Text(
            text = app.label,
            color = Color(0xFFE4E7DE),
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MainPanel(
    selectedApp: LauncherApp?,
    apps: List<LauncherApp>,
    onSelectApp: (LauncherApp) -> Unit,
    onLaunchApp: (LauncherApp) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(1.dp, Color(0xFF2A463A), RoundedCornerShape(22.dp))
            .background(Color(0xB30A1410), RoundedCornerShape(22.dp))
            .padding(14.dp)
    ) {
        CommandHeader(selectedApp = selectedApp)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoCard(
                title = "SYSTEM OVERVIEW",
                body = selectedApp?.let {
                    "Selected module: ${it.label}\nPackage: ${it.packageName}"
                } ?: "No module selected."
            )

            InfoCard(
                title = "QUICK ACTION",
                body = selectedApp?.let {
                    "Tap the launch tile below to open ${it.label}."
                } ?: "Select a module from the rail or grid."
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "MODULE GRID",
            color = Color(0xFFC6A56B),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(apps, key = { it.packageName }) { app ->
                ModuleTile(
                    app = app,
                    selected = selectedApp?.packageName == app.packageName,
                    onSelect = { onSelectApp(app) },
                    onLaunch = { onLaunchApp(app) }
                )
            }
        }
    }
}

@Composable
private fun CommandHeader(
    selectedApp: LauncherApp?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFF334B40), RoundedCornerShape(18.dp))
            .background(Color(0xAA101A15), RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = selectedApp?.label ?: "NO MODULE SELECTED",
                color = Color(0xFFF1F3EC),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = selectedApp?.packageName ?: "Awaiting module selection",
                color = Color(0xFF8BA08F),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(
            modifier = Modifier
                .border(1.dp, Color(0xFF486455), RoundedCornerShape(16.dp))
                .background(Color(0xFF101A15), RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = "READY",
                color = Color(0xFFC8D8B6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .border(1.dp, Color(0xFF2C4439), RoundedCornerShape(18.dp))
            .background(Color(0x880B1410), RoundedCornerShape(18.dp))
            .padding(14.dp)
    ) {
        Text(
            text = title,
            color = Color(0xFFC48D72),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = body,
            color = Color(0xFFDCE3D8),
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Composable
private fun ModuleTile(
    app: LauncherApp,
    selected: Boolean,
    onSelect: () -> Unit,
    onLaunch: () -> Unit
) {
    val borderColor = if (selected) Color(0xFF7CAB8A) else Color(0xFF2C4439)
    val backgroundColor = if (selected) Color(0xAA15221B) else Color(0x880A1310)

    Column(
        modifier = Modifier
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .background(backgroundColor, RoundedCornerShape(18.dp))
            .clickable(onClick = onSelect)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .border(1.dp, borderColor, RoundedCornerShape(14.dp))
                    .background(Color(0x22000000), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = app.icon,
                    contentDescription = app.label,
                    tint = Color(0xFFD0D5C8),
                    modifier = Modifier.size(28.dp)
                )
            }

            Box(
                modifier = Modifier
                    .border(1.dp, Color(0xFF425C50), RoundedCornerShape(10.dp))
                    .background(Color(0x99121C16), RoundedCornerShape(10.dp))
                    .clickable(onClick = onLaunch)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "OPEN",
                    color = Color(0xFFBBD0B4),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = app.label,
            color = Color(0xFFF0F3EA),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = app.packageName,
            color = Color(0xFF8FA190),
            fontSize = 11.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private fun iconForLabel(label: String): ImageVector {
    val lower = label.lowercase()

    return when {
        "system" in lower -> Icons.Default.Home
        "prop" in lower -> Icons.Default.Build
        "event" in lower -> Icons.Default.Event
        "booking" in lower -> Icons.Default.Inventory2
        "member" in lower -> Icons.Default.Groups
        "schedule" in lower -> Icons.Default.CalendarMonth
        else -> Icons.Default.Storage
    }
}
