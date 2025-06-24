package com.android.rickandmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val RickAndMortyLightColors = lightColorScheme(
    primary = RickGreen,
    onPrimary = RickBlack,
    secondary = RickPurple,
    onSecondary = RickCyan,
    background = Color.White,
    onBackground = RickBlack,
    surface = RickCyan,
    onSurface = RickBlack,
    tertiary = RickYellow
)

val RickAndMortyDarkColors = darkColorScheme(
    primary = RickGreen,
    onPrimary = RickBlack,
    secondary = RickPurple,
    onSecondary = RickCyan,
    background = RickBlack,
    onBackground = RickGreen,
    surface = RickPurple,
    onSurface = RickGreen,
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        when {
            darkTheme -> RickAndMortyDarkColors
            else -> RickAndMortyLightColors
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}