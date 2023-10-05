package com.technipixl.evalfinaleandroid.network.model


data class SearchMovieResponse(
    val results: MutableList<Movie>
)

data class Movie(
    val original_title: String?,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Float?,
)
