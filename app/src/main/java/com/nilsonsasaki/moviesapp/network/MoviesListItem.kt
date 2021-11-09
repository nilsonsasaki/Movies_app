package com.nilsonsasaki.moviesapp.network

import com.squareup.moshi.Json

data class MoviesListItem(

    val id: Int = 0,
    @Json(name = "vote_average") val rating: Float,
    val title: String = "",
    @Json(name = "poster_url") val coverUrl: String = "",
    val genres: List<String> = listOf(),
    @Json(name = "release_date") val releaseDate: String = "",
)
