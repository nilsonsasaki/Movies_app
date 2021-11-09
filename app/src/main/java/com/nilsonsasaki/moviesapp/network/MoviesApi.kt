package com.nilsonsasaki.moviesapp.network

object MoviesApi {
    val retrofitService: MoviesAppApiService by lazy {
        retrofit.create(MoviesAppApiService::class.java)
    }

}