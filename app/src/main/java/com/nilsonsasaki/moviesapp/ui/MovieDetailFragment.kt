package com.nilsonsasaki.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.nilsonsasaki.moviesapp.R
import com.nilsonsasaki.moviesapp.databinding.FragmentMovieDetailBinding
import com.nilsonsasaki.moviesapp.network.MovieDetails
import com.nilsonsasaki.moviesapp.viewmodels.MovieTitleViewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: MovieDetailFragmentArgs by navArgs()
    private val viewModel: MovieTitleViewModel by activityViewModels()

    private lateinit var movieDetails: MovieDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        viewModel.getMovieDetails(navigationArgs.movieId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movieDetails.observe(this.viewLifecycleOwner) { movie ->
            movieDetails = movie
            bind(movieDetails)
        }
    }

    private fun bind(movieDetails: MovieDetails) {
        binding.tvDetailsTitle.text = movieDetails.title
        binding.ivBackdrop.load(movieDetails.backdropUrl) {
            error(R.drawable.ic_rate_star)
        }
        binding.tvRating.text = movieDetails.rating.toString()
        binding.tvOverview.text = movieDetails.overview
        binding.tvGenres.text = movieDetails.genres.toString()
        binding.tvRuntime.text = movieDetails.runtime
        binding.tvReleaseYear.text = movieDetails.date
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}