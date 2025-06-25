package com.android.rickandmorty.repository

import com.android.rickandmorty.data.model.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): EpisodeResponse
}