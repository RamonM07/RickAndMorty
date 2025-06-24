package com.android.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.android.rickandmorty.ui.composables.Episode
import com.android.rickandmorty.ui.theme.RickAndMortyTheme
import com.android.rickandmorty.ui.view.ExploreScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    val episodes = listOf(
                        Episode(1, "Pilot", "S01E01", "December 2, 2013"),
                        Episode(2, "Lawnmower Dog", "S01E02", "December 9, 2013"),
                        Episode(3, "Anatomy Park", "S01E03", "December 16, 2013")
                        // ... cargar más desde red si quieres
                    )

                    ExploreScreen(
                        modifier = Modifier.padding(paddingValues),
                        episodes = episodes,
                        onEpisodeClick = { episode ->
                            // Aquí puedes navegar al detalle
                        }
                    )
                }
            }
        }
    }
}