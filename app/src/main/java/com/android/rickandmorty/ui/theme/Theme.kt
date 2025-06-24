package com.android.rickandmorty.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


val RickAndMortyLightColors = lightColorScheme(
    primary = OliveGreen,
    onPrimary = White,
    secondary = MustardYellow,
    onSecondary = NeutralBrown,
    background = SoftBeige,
    onBackground = NeutralBrown,
    surface = SoftBlue,
    onSurface = NeutralBrown,
    tertiary = NeutralBrown
)

val RickAndMortyDarkColors = darkColorScheme(
    primary = OliveGreen,
    onPrimary = Black,
    secondary = MustardYellow,
    onSecondary = Black,
    background = DarkBackground,
    onBackground = SoftBeige,
    surface = NeutralBrown,
    onSurface = SoftBeige,
    tertiary = SoftBlue
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