package com.nilsonsasaki.moviesapp.viewmodels

import androidx.lifecycle.*
import com.nilsonsasaki.moviesapp.network.MovieDetails
import com.nilsonsasaki.moviesapp.network.MoviesApi
import com.nilsonsasaki.moviesapp.network.MoviesListItem
import kotlinx.coroutines.launch

enum class MoviesAppApiStatus { LOADING, ERROR, DONE }

class MovieTitleViewModel : ViewModel() {

    private val _movieListStatus = MutableLiveData<MoviesAppApiStatus>()
    val movieListStatus: LiveData<MoviesAppApiStatus> = _movieListStatus

    private val _movieDetailsStatus = MutableLiveData<MoviesAppApiStatus>()
    val movieDetailsStatus: LiveData<MoviesAppApiStatus> = _movieDetailsStatus

    private val _moviesList = MutableLiveData<List<MoviesListItem>>()
    val moviesList: LiveData<List<MoviesListItem>> = _moviesList

    private val _moviesDetailsList = MutableLiveData<List<MovieDetails>>()
    val moviesDetailsList get() = _moviesDetailsList

    private var _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails get() = _movieDetails

    init {
        getMoviesList()
    }
    
    private fun getMoviesList() {
        viewModelScope.launch {
            _movieListStatus.value = MoviesAppApiStatus.LOADING
            try {
                _moviesList.value = MoviesApi.retrofitService.getMovieList()
                _movieListStatus.value = MoviesAppApiStatus.DONE
            } catch (e: Exception) {
                _movieListStatus.value = MoviesAppApiStatus.ERROR
                _moviesList.value = listOf()
            }
        }
    }

    fun getMovieDetails(movieId:Int){
        viewModelScope.launch {
            _movieDetailsStatus.value = MoviesAppApiStatus.LOADING
            try {
                _movieDetails.value = MoviesApi.retrofitService.getMovieDetails(movieId)
                _movieDetailsStatus.value = MoviesAppApiStatus.DONE
            } catch (e: Exception){
                _movieDetailsStatus.value = MoviesAppApiStatus.ERROR
                _movieDetails.value = MovieDetails("",
                listOf(),
                0,
                e.message!!,
                "",
                "",
                0.0f,
                "",)
            }
        }
    }
}