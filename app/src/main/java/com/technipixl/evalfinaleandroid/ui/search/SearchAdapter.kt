package com.technipixl.evalfinaleandroid.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.Utilities.roundFloatToString
import com.technipixl.evalfinaleandroid.Utilities.setupImage
import com.technipixl.evalfinaleandroid.databinding.SearchItemLayoutBinding
import com.technipixl.evalfinaleandroid.network.model.Movie
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class SearchAdapter(
    private val movieResponse: MovieResponse,
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClick
        )
    }

    override fun getItemCount(): Int {
        return movieResponse.results.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.setup(movieResponse.results[position])
    }
}