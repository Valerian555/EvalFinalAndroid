package com.technipixl.evalfinaleandroid.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.evalfinaleandroid.Utilities
import com.technipixl.evalfinaleandroid.Utilities.roundFloatToString
import com.technipixl.evalfinaleandroid.Utilities.setupImage
import com.technipixl.evalfinaleandroid.databinding.FragmentDetailBinding
import com.technipixl.evalfinaleandroid.network.model.MovieDetailResponse
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private var adapter: SimilarAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        if(Utilities.isNetworkAvailable(requireContext())) {
            //récupération des films similaires
            retrieveMovieDetail(args.movieId)
            retrieveSimilarMovies(args.movieId)
        } else {
            Utilities.showToast(requireContext())
        }

        //retour au fragment précédent
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun retrieveMovieDetail(movieID: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = Utilities.getMovieService().getMovieDetail(movieID, Utilities.API_KEY)
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
            val response = Utilities.getMovieService().getSimilarMovies(movieID, Utilities.API_KEY)
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

    private fun setupRecyclerView(moviesResponse: MovieResponse) {
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.HORIZONTAL, false)

        adapter = SimilarAdapter(moviesResponse) { movie ->
            movie.id?.let { goToDetails(it) }
        }
        binding.similarRecyclerView.adapter = adapter
    }

    private fun goToDetails(movieId: Long) {
        findNavController().navigate(DetailFragmentDirections.actionDetailFragmentSelf(movieId))
    }



    private fun setupUi(movieDetailResponse: MovieDetailResponse) {
        val baseImageUrl = "https://image.tmdb.org/t/p/w500"

        setupImage("$baseImageUrl${movieDetailResponse.backdrop_path}", binding.backgroundPoster)
        setupImage("$baseImageUrl${movieDetailResponse.poster_path}", binding.movieImage)

        binding.movieReview.text = movieDetailResponse.vote_average?.let { roundFloatToString(it).toString() }
        binding.movieName.text = movieDetailResponse.original_title
        binding.movieOverview.text = movieDetailResponse.overview
    }
}