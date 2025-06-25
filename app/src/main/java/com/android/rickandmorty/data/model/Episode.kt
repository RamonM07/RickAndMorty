package com.android.rickandmorty.data.model

data class EpisodeResponse(
    val info: Info,
    val results: List<Episode>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String
)

