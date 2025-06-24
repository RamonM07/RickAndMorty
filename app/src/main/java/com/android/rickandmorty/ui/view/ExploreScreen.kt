package com.android.rickandmorty.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.rickandmorty.ui.composables.Episode
import com.android.rickandmorty.ui.composables.EpisodeCard

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    episodes: List<Episode>,
    onEpisodeClick: (Episode) -> Unit
) {
    var searchQuery by remember{ mutableStateOf("") }

    val filteredEpisodes = episodes.filter { episode ->
        episode.name.contains(searchQuery, ignoreCase = true) ||
                episode.episode.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = modifier) {
        Text(
            text = "Explorar Episodios",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar por nombre o código") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(filteredEpisodes) { episode ->
                EpisodeCard(
                    episode = episode,
                    onClick = { onEpisodeClick(episode) }
                )
            }

            if (filteredEpisodes.isEmpty()) {
                item {
                    Text(
                        text = "No se encontraron episodios",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
