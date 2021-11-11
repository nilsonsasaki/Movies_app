package com.nilsonsasaki.moviesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.nilsonsasaki.moviesapp.R
import com.nilsonsasaki.moviesapp.databinding.FragmentMovieDetailBinding
import com.nilsonsasaki.moviesapp.network.MovieDetails
import com.nilsonsasaki.moviesapp.viewmodels.MovieTitleViewModel
import com.nilsonsasaki.moviesapp.viewmodels.MoviesAppApiStatus

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieTitleViewModel by activityViewModels()

    private lateinit var movieDetails: MovieDetails
    private lateinit var movieDetailStatus: MoviesAppApiStatus
    private var stopRetry = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        viewModel.setMovieId(navigationArgs.movieId)
        viewModel.getMovieDetails()
        viewModel.clearRetryCounter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movieDetailsStatus.observe(this.viewLifecycleOwner){status->
            movieDetailStatus = status
            when (movieDetailStatus) {
                MoviesAppApiStatus.LOADING -> {
                    binding.ivDetailsFragmentStatus.visibility = CircularProgressIndicator.VISIBLE
                    binding.loadedDetailFragment.visibility=ScrollView.GONE
                }
                MoviesAppApiStatus.DONE -> {
                    binding.ivDetailsFragmentStatus.visibility = CircularProgressIndicator.GONE
                    binding.loadedDetailFragment.visibility = ScrollView.VISIBLE
                }
                else -> {
                    viewModel.retryGetMoviesDetails()
                }
            }
        }

        viewModel.stopRetrying.observe(this.viewLifecycleOwner) { stop ->
            stopRetry = stop
            if(stopRetry){
                val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToErrorFragment()
                this.findNavController().navigate(action)
            }
        }

        viewModel.movieDetails.observe(this.viewLifecycleOwner) { movie ->
            movieDetails = movie
            bind(movieDetails)
        }
    }

    private fun bind(movieDetails: MovieDetails) {
        binding.tvDetailsTitle.text = movieDetails.title
        binding.ivBackdrop.load(movieDetails.backdropUrl) {
            error(R.drawable.ic_broken_image)
            placeholder(R.drawable.loading_animation)
        }
        binding.tvRating.text = movieDetails.rating.toString()
        binding.tvOverview.text = movieDetails.overview
        binding.tvGenres.text = movieDetails.genres.toString()
        binding.tvRuntime.text = movieDetails.runtime
        binding.tvReleaseYear.text = movieDetails.date
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearRetryCounter()
        viewModel.clearMovieDetails()
        viewModel.clearMovieDetailsStatus()
        _binding = null
    }

}