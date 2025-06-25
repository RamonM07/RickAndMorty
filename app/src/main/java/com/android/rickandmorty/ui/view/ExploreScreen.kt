package com.android.rickandmorty.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.rickandmorty.R
import com.android.rickandmorty.data.model.Episode
import com.android.rickandmorty.ui.composables.EpisodeCard
import com.android.rickandmorty.ui.view.viewModel.EpisodesViewModel

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    viewModel: EpisodesViewModel = hiltViewModel(),
    onEpisodeClick: (Episode) -> Unit
) {
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    var searchQuery by remember { mutableStateOf("") }

    val filteredEpisodes = episodes.itemSnapshotList.items.filter { episode ->
        episode.name.contains(searchQuery, ignoreCase = true) ||
                episode.episode.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_explore_episodes),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(stringResource(R.string.message_serch_field)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (searchQuery.isNotEmpty()) {
                if (filteredEpisodes.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.message_empty_filtered),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                } else {
                    items(filteredEpisodes) { episode ->
                        EpisodeCard(episode = episode, onClick = { onEpisodeClick(episode) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

            } else {
                items(episodes.itemCount) { index ->
                    val episode = episodes.itemSnapshotList.items[index]
                    episode.let {
                        EpisodeCard(episode = it, onClick = { onEpisodeClick(it) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                episodes.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> item {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }

                        is LoadState.Error -> item {
                            Text(stringResource(R.string.message_error_in_load))
                        }

                        else -> {}
                    }
                }
            }
        }

    }
}
