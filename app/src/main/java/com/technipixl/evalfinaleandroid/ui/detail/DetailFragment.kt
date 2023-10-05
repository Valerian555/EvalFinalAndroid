package com.technipixl.evalfinaleandroid.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.databinding.FragmentDetailBinding
import com.technipixl.evalfinaleandroid.network.model.MovieDetailResponse
import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import com.technipixl.evalfinaleandroid.network.model.SimilarMoviesResponse
import com.technipixl.evalfinaleandroid.network.service.MovieServiceImpl
import com.technipixl.evalfinaleandroid.ui.search.SearchAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.math.roundToInt

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val movieServiceImpl by lazy { MovieServiceImpl() }
    private val apiKey = "55530312075972a425f5fa13e21b218f"
    private var adapter: SimilarAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        retrieveMovieDetail(args.movieId)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        retrieveSimilarMovies(args.movieId)

        return binding.root
    }

    private fun retrieveMovieDetail(movieID: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieServiceImpl.getMovieDetail(movieID, apiKey)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { setupUi(it) }
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }

    private fun retrieveSimilarMovies(movieID: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieServiceImpl.getSimilarMovies(movieID, apiKey)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body().let {
                            if (it != null) {
                                setupRecyclerView(it)
                            }
                        }
                    }
                } catch (e: HttpException) {
                    print(e)
                } catch (e: Throwable) {
                    print(e)
                }
            }
        }
    }

    private fun setupRecyclerView(similarMoviesResponse: SimilarMoviesResponse) {
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.HORIZONTAL, false)

        //3. ajouter la variable
        adapter = SimilarAdapter(similarMoviesResponse) { movie ->
            //go to similarMovies details
        }
        binding.similarRecyclerView.adapter = adapter
    }



    private fun setupUi(movieDetailResponse: MovieDetailResponse) {
        val baseImageUrl = "https://image.tmdb.org/t/p/w500"

        setupImage("$baseImageUrl${movieDetailResponse.backdrop_path}", binding.backgroundPoster)
        setupImage("$baseImageUrl${movieDetailResponse.poster_path}", binding.movieImage)

        binding.movieReview.text = movieDetailResponse.vote_average?.let { roundFloatToString(it).toString() }
        binding.movieName.text = movieDetailResponse.original_title
        binding.movieOverview.text = movieDetailResponse.overview
    }

    private fun roundFloatToString(number: Float): Float {
        return (number * 10.0f).roundToInt() / 10.0f
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