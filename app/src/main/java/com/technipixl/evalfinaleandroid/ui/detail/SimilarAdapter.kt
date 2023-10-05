package com.technipixl.evalfinaleandroid.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.Utilities.setupImage
import com.technipixl.evalfinaleandroid.databinding.SimilarItemLayoutBinding
import com.technipixl.evalfinaleandroid.network.model.Movie
import com.technipixl.evalfinaleandroid.network.model.MovieResponse

class SimilarAdapter(
    private val moviesResponse: MovieResponse,
    private val onItemClick: (Movie) -> Unit
): RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {

    class SimilarViewHolder(
        private val binding: SimilarItemLayoutBinding,
        private val onItemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setup(movie: Movie) {
            binding.movieTitle.text = movie.original_title
            movie.poster_path?.let { setupImage(it, binding.movieImage) }

            binding.container.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        return SimilarViewHolder(
            SimilarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return moviesResponse.results.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.setup(moviesResponse.results[position])
    }
}