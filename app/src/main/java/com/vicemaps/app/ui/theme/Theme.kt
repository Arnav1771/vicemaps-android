package com.vicemaps.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonMagenta,
    tertiary = NeonAmber,
    background = BgDeep,
    surface = BgHud,
    onPrimary = BgDeep,
    onSecondary = TextPrimary,
    onTertiary = BgDeep,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun ViceMapsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}
