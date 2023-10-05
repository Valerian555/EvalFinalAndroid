package com.technipixl.evalfinaleandroid.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technipixl.evalfinaleandroid.databinding.FragmentSearchBinding
import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import com.technipixl.evalfinaleandroid.network.service.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val movieServiceImpl by lazy { MovieServiceImpl() }
    private val apiKey = "55530312075972a425f5fa13e21b218f"
    var adapter: SearchAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        setupTextWatcher()

        return binding.root
    }

    private fun retrieveMovieBySearch(search: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieServiceImpl.getMoviesBySearch(apiKey, search)
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

    private fun setupRecyclerView(searchMovieResponse: SearchMovieResponse) {
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false)

        //3. ajouter la variable
        adapter = SearchAdapter(searchMovieResponse) { movie ->
            //goToDetail(movie)
        }
        binding.searchRecyclerView.adapter = adapter
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