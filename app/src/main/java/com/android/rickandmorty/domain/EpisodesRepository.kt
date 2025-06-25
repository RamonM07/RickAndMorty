package com.android.rickandmorty.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.rickandmorty.data.model.Episode
import com.android.rickandmorty.repository.RickAndMortyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesRepository @Inject constructor(private val api: RickAndMortyService) {
    fun getEpisodes(): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 3),
            pagingSourceFactory = { EpisodesPagingSource(api) }
        ).flow
    }
}