package com.nilsonsasaki.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nilsonsasaki.moviesapp.databinding.FragmentMoviesListBinding
import com.nilsonsasaki.moviesapp.ui.adapters.MoviesListAdapter
import com.nilsonsasaki.moviesapp.viewmodels.MovieTitleViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: MovieTitleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMoviesListBinding.inflate(layoutInflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvMoviesList
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.moviesList.observe(this.viewLifecycleOwner) { movie ->
            recyclerView.adapter = MoviesListAdapter(movie){
                val action = MovieListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(it.id)
                this.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}