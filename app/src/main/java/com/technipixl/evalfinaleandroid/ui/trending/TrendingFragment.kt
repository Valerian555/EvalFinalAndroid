package com.technipixl.evalfinaleandroid.ui.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.technipixl.evalfinaleandroid.Utilities
import com.technipixl.evalfinaleandroid.databinding.FragmentTrendingBinding
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import com.technipixl.evalfinaleandroid.network.service.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private var adapter: TrendingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(layoutInflater)

        if (Utilities.isNetworkAvailable(requireContext())) {
            retrieveTrendingMovies()
        } else {
            Utilities.showToast(requireContext())
        }

        return binding.root
    }

    private fun retrieveTrendingMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = Utilities.getMovieService().getTrendingMovies("day", Utilities.API_KEY)
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

    private fun setupRecyclerView(movieResponse: MovieResponse) {
        binding.trendingRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = TrendingAdapter(movieResponse) { movie ->
            // détail
        }
        binding.trendingRecyclerView.adapter = adapter
    }
}