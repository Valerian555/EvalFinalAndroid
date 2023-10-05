package com.technipixl.evalfinaleandroid.ui.trending

import com.technipixl.evalfinaleandroid.databinding.TrendingItemLayoutBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.Utilities.roundFloatToString
import com.technipixl.evalfinaleandroid.Utilities.setupImage
import com.technipixl.evalfinaleandroid.network.model.Movie
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import kotlin.math.roundToInt

class TrendingAdapter(
    private val trendingMovieResponse: MovieResponse,
    private val onItemClick: (Movie) -> Unit
): RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {

    class TrendingViewHolder(
        private val binding: TrendingItemLayoutBinding,
        private val onItemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setup(movie: Movie) {
            binding.movieReview.text = movie.vote_average?.let { roundFloatToString(it).toString() }
            movie.poster_path?.let { setupImage(it, binding.movieImage) }

            binding.container.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder(
            TrendingItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return trendingMovieResponse.results.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.setup(trendingMovieResponse.results[position])
    }
}