package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CosmicCyberColorScheme = lightColorScheme(
    primary = NeonCyan,
    secondary = NeonPink,
    tertiary = BrightGold,
    background = CosmicSpaceBg,
    surface = CosmicSpaceCard,
    onPrimary = CosmicSpaceBg,
    onSecondary = CosmicSpaceBg,
    onBackground = SoftWhite,
    onSurface = SoftWhite
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CosmicCyberColorScheme,
        typography = Typography,
        content = content
    )
}
