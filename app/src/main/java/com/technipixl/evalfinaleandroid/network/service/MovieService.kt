package com.technipixl.evalfinaleandroid.network.service

import com.technipixl.evalfinaleandroid.network.model.MovieDetailResponse
import com.technipixl.evalfinaleandroid.network.model.SearchMovieResponse
import com.technipixl.evalfinaleandroid.network.model.SimilarMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("search/movie")
    suspend fun getMoviesBySearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<SearchMovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<SimilarMoviesResponse>
}