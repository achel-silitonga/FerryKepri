package com.example.ferrykepri.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val FerryColorScheme = lightColorScheme(
    primary = FerryBlue700,
    onPrimary = FerryWhite,
    primaryContainer = FerrySky100,
    onPrimaryContainer = FerryBlue900,
    secondary = FerryBlue500,
    onSecondary = FerryWhite,
    secondaryContainer = FerrySky100,
    onSecondaryContainer = FerryBlue900,
    tertiary = FerryTeal,
    onTertiary = FerryWhite,
    background = FerrySky50,
    onBackground = FerryNavy,
    surface = FerryWhite,
    onSurface = FerryNavy,
    surfaceVariant = FerrySky100,
    onSurfaceVariant = FerryMuted,
    outline = FerryBorder,
    outlineVariant = FerryBorder,
    error = FerryRed,
    onError = FerryWhite,
    errorContainer = FerryRedSoft,
    onErrorContainer = FerryRed
)

@Composable
fun FerrykepriTheme(
    @Suppress("UNUSED_PARAMETER") darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FerryColorScheme,
        typography = Typography,
        content = content
    )
}
