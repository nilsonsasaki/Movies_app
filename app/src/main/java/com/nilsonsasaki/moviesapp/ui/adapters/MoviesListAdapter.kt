package com.nilsonsasaki.moviesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nilsonsasaki.moviesapp.R
import com.nilsonsasaki.moviesapp.databinding.ListItemBinding
import com.nilsonsasaki.moviesapp.network.MoviesListItem
import com.nilsonsasaki.moviesapp.ui.adapters.MoviesListAdapter.MoviesListViewHolder


class MoviesListAdapter(private val list: List<MoviesListItem>,
private val onItemClicked: (MoviesListItem)-> Unit) :
    RecyclerView.Adapter<MoviesListViewHolder>() {

    class MoviesListViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moviesListItem: MoviesListItem) {
            binding.tvTitle.text = moviesListItem.title
            binding.ivCover.load(moviesListItem.coverUrl.toUri()){
                error(R.drawable.ic_broken_image)
                placeholder(R.drawable.loading_animation)
            }
            binding.tvRating.text = moviesListItem.rating.toString()
            binding.tvReleaseYear.text = moviesListItem.releaseDate
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MoviesListViewHolder {
        val viewHolder= MoviesListViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent,false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(list[position])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}