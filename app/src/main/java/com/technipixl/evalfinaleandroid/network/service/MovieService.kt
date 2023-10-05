package com.technipixl.evalfinaleandroid.network.service

import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("search/movie")
    suspend fun getMoviesBySearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<SearchMovieResponse>
}