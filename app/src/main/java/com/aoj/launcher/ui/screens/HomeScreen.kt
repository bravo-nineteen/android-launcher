package com.aoj.launcher.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.aoj.launcher.model.AppInfo
import com.aoj.launcher.ui.components.AppTile
import com.aoj.launcher.ui.components.OperationalBackground
import com.aoj.launcher.ui.components.SettingsPanel
import com.aoj.launcher.ui.components.StatusHeader
import com.aoj.launcher.ui.theme.TacticalKhaki
import com.aoj.launcher.ui.theme.TacticalLine
import com.aoj.launcher.ui.theme.TacticalMutedText
import com.aoj.launcher.ui.theme.TacticalPanel
import com.aoj.launcher.ui.theme.TacticalText

@Composable
fun HomeScreen(
    apps: List<AppInfo>,
    adminMode: Boolean,
    showWatermark: Boolean,
    showBackgroundImage: Boolean,
    showSettings: Boolean,
    onRefresh: () -> Unit,
    onLaunchApp: (AppInfo) -> Unit,
    onToggleSettings: () -> Unit,
    onSetAdminMode: (Boolean) -> Unit,
    onSetWatermark: (Boolean) -> Unit,
    onSetBackgroundImage: (Boolean) -> Unit,
    onTogglePinned: (String) -> Unit,
    onToggleHidden: (String) -> Unit,
    pinnedPackages: Set<String>,
    hiddenPackages: Set<String>
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val isCompact = screenWidthDp < 700

    Box(modifier = Modifier.fillMaxSize()) {
        OperationalBackground(
            showBackgroundImage = showBackgroundImage,
            showWatermark = showWatermark
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            StatusHeader(
                isCompact = isCompact,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (isCompact) {
                CompactLauncherBody(
                    apps = apps,
                    adminMode = adminMode,
                    showSettings = showSettings,
                    onRefresh = onRefresh,
                    onLaunchApp = onLaunchApp,
                    onToggleSettings = onToggleSettings,
                    onSetAdminMode = onSetAdminMode,
                    onSetWatermark = onSetWatermark,
                    onSetBackgroundImage = onSetBackgroundImage,
                    onTogglePinned = onTogglePinned,
                    onToggleHidden = onToggleHidden,
                    pinnedPackages = pinnedPackages,
                    showWatermark = showWatermark,
                    showBackgroundImage = showBackgroundImage
                )
            } else {
                TabletLauncherBody(
                    apps = apps,
                    adminMode = adminMode,
                    showSettings = showSettings,
                    onRefresh = onRefresh,
                    onLaunchApp = onLaunchApp,
                    onToggleSettings = onToggleSettings,
                    onSetAdminMode = onSetAdminMode,
                    onSetWatermark = onSetWatermark,
                    onSetBackgroundImage = onSetBackgroundImage,
                    onTogglePinned = onTogglePinned,
                    onToggleHidden = onToggleHidden,
                    pinnedPackages = pinnedPackages,
                    showWatermark = showWatermark,
                    showBackgroundImage = showBackgroundImage
                )
            }
        }
    }
}

@Composable
private fun CompactLauncherBody(
    apps: List<AppInfo>,
    adminMode: Boolean,
    showSettings: Boolean,
    onRefresh: () -> Unit,
    onLaunchApp: (AppInfo) -> Unit,
    onToggleSettings: () -> Unit,
    onSetAdminMode: (Boolean) -> Unit,
    onSetWatermark: (Boolean) -> Unit,
    onSetBackgroundImage: (Boolean) -> Unit,
    onTogglePinned: (String) -> Unit,
    onToggleHidden: (String) -> Unit,
    pinnedPackages: Set<String>,
    showWatermark: Boolean,
    showBackgroundImage: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(10.dp))
            .background(TacticalPanel, RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        ControlBar(
            appCount = apps.size,
            onRefresh = onRefresh,
            onToggleSettings = onToggleSettings,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (showSettings) {
            SettingsPanel(
                adminMode = adminMode,
                showWatermark = showWatermark,
                showBackgroundImage = showBackgroundImage,
                onSetAdminMode = onSetAdminMode,
                onSetWatermark = onSetWatermark,
                onSetBackgroundImage = onSetBackgroundImage,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(apps, key = { it.packageName }) { app ->
                AppTile(
                    app = app,
                    isPinned = pinnedPackages.contains(app.packageName),
                    adminMode = adminMode,
                    onClick = { onLaunchApp(app) },
                    onTogglePinned = { onTogglePinned(app.packageName) },
                    onToggleHidden = { onToggleHidden(app.packageName) }
                )
            }
        }
    }
}

@Composable
private fun TabletLauncherBody(
    apps: List<AppInfo>,
    adminMode: Boolean,
    showSettings: Boolean,
    onRefresh: () -> Unit,
    onLaunchApp: (AppInfo) -> Unit,
    onToggleSettings: () -> Unit,
    onSetAdminMode: (Boolean) -> Unit,
    onSetWatermark: (Boolean) -> Unit,
    onSetBackgroundImage: (Boolean) -> Unit,
    onTogglePinned: (String) -> Unit,
    onToggleHidden: (String) -> Unit,
    pinnedPackages: Set<String>,
    showWatermark: Boolean,
    showBackgroundImage: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(10.dp))
            .background(TacticalPanel, RoundedCornerShape(10.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.28f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoPanel(
                title = "DEVICE STATUS",
                body = "Launcher armed and running. Optimized for large-screen field terminal use."
            )
            InfoPanel(
                title = "UI MODE",
                body = "Rugged operational tablet profile. Hard-edged tactical presentation with minimal distractions."
            )
            InfoPanel(
                title = "ADMIN ACTIONS",
                body = "Long-press app tile to pin or unpin. Use HIDE to remove an app from view while admin mode is active."
            )

            if (showSettings) {
                SettingsPanel(
                    adminMode = adminMode,
                    showWatermark = showWatermark,
                    showBackgroundImage = showBackgroundImage,
                    onSetAdminMode = onSetAdminMode,
                    onSetWatermark = onSetWatermark,
                    onSetBackgroundImage = onSetBackgroundImage
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.72f)
                .fillMaxSize()
        ) {
            ControlBar(
                appCount = apps.size,
                onRefresh = onRefresh,
                onToggleSettings = onToggleSettings,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            AppColumns(
                apps = apps,
                adminMode = adminMode,
                onLaunchApp = onLaunchApp,
                onTogglePinned = onTogglePinned,
                onToggleHidden = onToggleHidden,
                pinnedPackages = pinnedPackages,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun AppColumns(
    apps: List<AppInfo>,
    adminMode: Boolean,
    onLaunchApp: (AppInfo) -> Unit,
    onTogglePinned: (String) -> Unit,
    onToggleHidden: (String) -> Unit,
    pinnedPackages: Set<String>,
    modifier: Modifier = Modifier
) {
    val chunked = apps.chunked(5)

    Row(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        chunked.forEach { columnApps ->
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                columnApps.forEach { app ->
                    AppTile(
                        app = app,
                        isPinned = pinnedPackages.contains(app.packageName),
                        adminMode = adminMode,
                        onClick = { onLaunchApp(app) },
                        onTogglePinned = { onTogglePinned(app.packageName) },
                        onToggleHidden = { onToggleHidden(app.packageName) },
                        modifier = Modifier.fillMaxWidth(0.18f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ControlBar(
    appCount: Int,
    onRefresh: () -> Unit,
    onToggleSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, TacticalLine), RoundedCornerShape(8.dp))
            .background(TacticalPanel, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "APP INVENTORY",
                color = TacticalKhaki,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$appCount visible launchable apps detected",
                color = TacticalMutedText,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = onToggleSettings,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TacticalLine,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
                Text(text = " SETTINGS")
            }

            Button(
                onClick = onRefresh,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TacticalKhaki,
                    contentColor = Color.Black
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                Text(text = " REFRESH")
            }
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
