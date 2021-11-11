package com.nilsonsasaki.moviesapp.viewmodels

import androidx.lifecycle.*
import com.nilsonsasaki.moviesapp.network.MovieDetails
import com.nilsonsasaki.moviesapp.network.MoviesApi
import com.nilsonsasaki.moviesapp.network.MoviesListItem
import kotlinx.coroutines.launch

enum class MoviesAppApiStatus { LOADING, ERROR, DONE }

enum class MoviesAppScreen { LIST, DETAILS }

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

    private var _stopRetrying = MutableLiveData<Boolean>()
    val stopRetrying get() = _stopRetrying

    private var _retryCounter: Int = 0

    private var _appScreen = MutableLiveData<MoviesAppScreen>()
    val appScreen get() = _appScreen

    private var _movieId = MutableLiveData<Int>()
    val movieId get() = _movieId

    init {
        getMoviesList()
    }

    fun clearRetryCounter() {
        _retryCounter = 0
        _stopRetrying.value = false
    }

    fun clearMovieDetailsStatus() {
        _movieDetailsStatus.value = MoviesAppApiStatus.LOADING
    }

    fun setAppScreen(screen: MoviesAppScreen) {
        _appScreen.value = screen
    }

    fun setMovieId(id: Int) {
        _movieId.value = id
    }

    fun clearMovieId() {
        _movieId.value = 0
    }

    fun clearMovieDetails() {
        _movieDetails.value = MovieDetails(
            "",
            listOf(),
            0,
            "Unable to Load",
            "",
            "",
            0.0f,
            "",
        )
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

    fun retryGetMoviesList() {
        if (_retryCounter < 3) {
            _movieListStatus.value = MoviesAppApiStatus.LOADING
            getMoviesList()
            _retryCounter++
        } else {
            _stopRetrying.value = true
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            _movieDetailsStatus.value = MoviesAppApiStatus.LOADING
            try {
                _movieDetails.value = MoviesApi.retrofitService.getMovieDetails(movieId)
                _movieDetailsStatus.value = MoviesAppApiStatus.DONE
            } catch (e: Exception) {
                _movieDetailsStatus.value = MoviesAppApiStatus.ERROR
                _movieDetails.value = _emptyMovieDetails
            }
        }
    }

    private val _emptyMovieDetails = MovieDetails(
        "",
        listOf(),
        0,
        "",
        "",
        "",
        0.0f,
        "",
    )
}