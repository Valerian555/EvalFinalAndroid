package com.technipixl.evalfinaleandroid.network.model

data class MovieDetailResponse(
    val original_title: String?,
    val backdrop_path: String?,
    val poster_path: String?,
    val vote_average: Float?,
    val overview: String?
)
