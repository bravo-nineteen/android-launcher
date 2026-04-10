package com.aoj.launcher.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = TacticalKhaki,
    secondary = TacticalSand,
    background = TacticalBlack,
    surface = TacticalOlive,
    onPrimary = TacticalBlack,
    onSecondary = TacticalBlack,
    onBackground = TacticalText,
    onSurface = TacticalText
)

@Composable
fun AOJLauncherTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme || isSystemInDarkTheme()) DarkColors else DarkColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
