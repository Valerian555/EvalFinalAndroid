package com.technipixl.evalfinaleandroid.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.databinding.SearchItemLayoutBinding
import com.technipixl.evalfinaleandroid.databinding.SimilarItemLayoutBinding
import com.technipixl.evalfinaleandroid.network.model.Movie
import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import com.technipixl.evalfinaleandroid.network.model.SimilarMovie
import com.technipixl.evalfinaleandroid.network.model.SimilarMoviesResponse
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class SimilarAdapter(
    private val similarMoviesResponse: SimilarMoviesResponse,
    private val onItemClick: (SimilarMovie) -> Unit
): RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>() {
    class SimilarViewHolder(
        private val binding: SimilarItemLayoutBinding,
        private val onItemClick: (SimilarMovie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setup(similarMovie: SimilarMovie) {
            binding.movieTitle.text = similarMovie.original_title
            similarMovie.poster_path?.let { setupImage(it, binding.movieImage) }

            binding.container.setOnClickListener {
                onItemClick(similarMovie)
            }
        }

        private fun setupImage(url: String, imageView: ImageView) {
            val baseUrlImage = "https://image.tmdb.org/t/p/w500"
            Picasso.get()
                .load("$baseUrlImage$url")
                .fit()
                .centerCrop()
                .into(imageView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        return SimilarViewHolder(
            SimilarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return similarMoviesResponse.results.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.setup(similarMoviesResponse.results[position])
    }
}