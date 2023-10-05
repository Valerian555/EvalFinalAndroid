package com.technipixl.evalfinaleandroid.network.service

import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import retrofit2.Response

class MovieServiceImpl: BaseServiceImpl() {

    suspend fun getMoviesBySearch(apiKey: String, query: String): Response<SearchMovieResponse> =
        getRetrofit().create(MovieService::class.java).getMoviesBySearch(apiKey, query)
}