package com.nilsonsasaki.moviesapp.network

import com.squareup.moshi.Json

data class MovieDetails(
    @Json(name = "backdrop_url") val backdropUrl: String = "",
    val genres: List<String> = listOf(),
    val id: Int = 0,
    val overview: String = "",
    @Json(name = "release_date") val date: String = "",
    val title: String = "",
    @Json(name = "vote_average") val rating: Float,
    val runtime: String = ""
)

