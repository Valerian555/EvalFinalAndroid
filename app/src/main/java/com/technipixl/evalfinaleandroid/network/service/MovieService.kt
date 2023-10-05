package com.technipixl.evalfinaleandroid.network.service

import com.technipixl.evalfinaleandroid.network.model.MovieDetailResponse
import com.technipixl.evalfinaleandroid.network.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    //endpoint pour trouver une liste de films en fonction d'un input
    @GET("search/movie")
    suspend fun getMoviesBySearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<MovieResponse>

    //endpoint permettant de récupérer les détails d'un film en particulier via son id
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailResponse>

    //endpoint permettant de récupérer une liste de films similaires à un id
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    //endpoint permettant de récupérer une liste de films populaires aujourd'hui
    @GET("trending/all/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>
}