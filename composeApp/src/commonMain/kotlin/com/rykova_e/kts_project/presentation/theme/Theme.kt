package com.rykova_e.kts_project.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

private val DarkColorScheme = darkColorScheme(
    onSecondary = md_theme_light_onSecondary,
    background = md_theme_dark_background,
    surfaceTint = md_theme_dark_surfaceTint
)
private val LightColorScheme = lightColorScheme(
    onSecondary = md_theme_dark_onSecondary,
    background = md_theme_light_background,
    surfaceTint = md_theme_light_surfaceTint
)

val MaterialAppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = font_size_16
    )
)

@Composable
fun AppThemeMaterial(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialAppTypography,
        shapes = MaterialAppShapes,
        content = content,
    )
}