package com.technipixl.evalfinaleandroid.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.evalfinaleandroid.Utilities
import com.technipixl.evalfinaleandroid.databinding.FragmentSearchBinding
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import com.technipixl.evalfinaleandroid.network.service.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private var adapter: SearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        //écoute du changemet d'état de l'edit text
        setupTextWatcher()

        return binding.root
    }

    private fun retrieveMovieBySearch(search: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = Utilities.getMovieService().getMoviesBySearch(Utilities.API_KEY, search)
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
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false)

        adapter = SearchAdapter(movieResponse) { movie ->
            //permet la navigation vers le fragment detail (avec l'id)
            movie.id?.let { goToDetail(it) }
        }
        binding.searchRecyclerView.adapter = adapter

        //gestion de l'affichage de la vue si la recyclerView est vide
        if (movieResponse.results.isNotEmpty()) {
            binding.noDataView.visibility = View.INVISIBLE
        } else {
            binding.noDataView.visibility = View.VISIBLE
        }
    }

    private fun goToDetail(movieId: Long) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieId))
    }

    private fun setupTextWatcher() {
        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchInput = binding.searchInput.text.toString()
                retrieveMovieBySearch(searchInput)
            }
        })
    }
}