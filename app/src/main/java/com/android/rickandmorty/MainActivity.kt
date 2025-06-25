package com.android.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.android.rickandmorty.ui.theme.RickAndMortyTheme
import com.android.rickandmorty.ui.view.ExploreScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
                    ExploreScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        onEpisodeClick = {})
                }
            }
        }
    }
}