package com.nilsonsasaki.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nilsonsasaki.moviesapp.databinding.FragmentErrorBinding
import com.nilsonsasaki.moviesapp.viewmodels.MovieTitleViewModel

class ErrorFragment : Fragment() {

    private var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieTitleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btReconnectButton.setOnClickListener {
            viewModel.clearRetryCounter()
            viewModel.clearMovieListStatus()
            viewModel.getMoviesList()
            val action = ErrorFragmentDirections.actionErrorFragmentToMoviesListFragment()
            this.findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}