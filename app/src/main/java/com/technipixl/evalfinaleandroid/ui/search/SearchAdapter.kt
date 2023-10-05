package com.technipixl.evalfinaleandroid.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.databinding.SearchItemLayoutBinding
import com.technipixl.evalfinaleandroid.network.model.Movie
import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class SearchAdapter(
    private val searchMovieResponse: SearchMovieResponse,
    private val onItemClick: (Movie) -> Unit
): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(
        private val binding: SearchItemLayoutBinding,
        private val onItemClick: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setup(movie: Movie) {

            binding.movieName.text = movie.original_title
            binding.movieReleaseDate.text = movie.release_date?.let { formatDate(it) }
            binding.movieReview.text = movie.vote_average?.let { roundFloatToString(it).toString() }
            movie.poster_path?.let { setupImage(it, binding.movieImage) }

            binding.container.setOnClickListener {
                onItemClick(movie)
            }
        }

        private fun roundFloatToString(number: Float): Float {
            return (number * 10.0f).roundToInt() / 10.0f
        }

        private fun formatDate(dateString: String): String {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("MMMM, dd yyyy", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                return outputFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return searchMovieResponse.results.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.setup(searchMovieResponse.results[position])
    }
}