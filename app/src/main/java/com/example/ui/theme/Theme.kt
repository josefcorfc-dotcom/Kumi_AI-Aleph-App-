package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CyberColorScheme = darkColorScheme(
    primary = CyberCyan,
    onPrimary = Color.White,
    primaryContainer = CyberCyanDark,
    secondary = CyberEmerald,
    onSecondary = Color.White,
    tertiary = CyberPurple,
    background = CyberBackground,
    onBackground = CyberTextWhite,
    surface = CyberSurface,
    onSurface = CyberTextWhite,
    surfaceVariant = CyberSurfaceVariant,
    onSurfaceVariant = CyberTextMuted,
    outline = CyberSurfaceBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
