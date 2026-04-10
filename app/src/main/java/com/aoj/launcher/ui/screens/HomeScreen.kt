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
}
