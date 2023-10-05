package com.technipixl.evalfinaleandroid.network.service

import com.technipixl.evalfinaleandroid.network.model.MovieDetailResponse
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import retrofit2.Response

class MovieServiceImpl: BaseServiceImpl() {
    suspend fun getMoviesBySearch(apiKey: String, query: String): Response<MovieResponse> =
        getRetrofit().create(MovieService::class.java).getMoviesBySearch(apiKey, query)

    suspend fun getMovieDetail(movieId: Long, apiKey: String): Response<MovieDetailResponse> =
        getRetrofit().create(MovieService::class.java).getMovieDetails(movieId, apiKey)

    suspend fun getSimilarMovies(movieId: Long, apiKey: String): Response<MovieResponse> =
        getRetrofit().create(MovieService::class.java).getSimilarMovies(movieId, apiKey)

    suspend fun getTrendingMovies(time: String, apiKey: String): Response<MovieResponse> =
        getRetrofit().create(MovieService::class.java).getTrendingMovies(time, apiKey)
}