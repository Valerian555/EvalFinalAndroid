package com.technipixl.evalfinaleandroid.network.service

import com.technipixl.evalfinaleandroid.network.model.MovieDetailResponse
import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import com.technipixl.evalfinaleandroid.network.model.SimilarMoviesResponse
import retrofit2.Response

class MovieServiceImpl: BaseServiceImpl() {

    suspend fun getMoviesBySearch(apiKey: String, query: String): Response<SearchMovieResponse> =
        getRetrofit().create(MovieService::class.java).getMoviesBySearch(apiKey, query)

    suspend fun getMovieDetail(movieId: Long, apiKey: String): Response<MovieDetailResponse> =
        getRetrofit().create(MovieService::class.java).getMovieDetails(movieId, apiKey)

    suspend fun getSimilarMovies(movieId: Long, apiKey: String): Response<SimilarMoviesResponse> =
        getRetrofit().create(MovieService::class.java).getSimilarMovies(movieId, apiKey)
}