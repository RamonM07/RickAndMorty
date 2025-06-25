package com.android.rickandmorty.ui.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.android.rickandmorty.domain.EpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    repository: EpisodesRepository
) : ViewModel() {
    val episodes = repository.getEpisodes().cachedIn(viewModelScope)
}
