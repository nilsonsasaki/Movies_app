package com.nilsonsasaki.moviesapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MoviesAppApiService{

    @GET("movies")
    suspend fun getMovieList():List<MoviesListItem>

    @GET("movies/{title_id}")
    suspend fun getMovieDetails(@Path("title_id")id:Int):MovieDetails
}
