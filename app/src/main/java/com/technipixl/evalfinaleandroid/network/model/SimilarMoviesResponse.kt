package com.technipixl.evalfinaleandroid.network.model

data class SimilarMoviesResponse(
    val results: MutableList<SimilarMovie>
)

data class SimilarMovie(
    val id: Long?,
    val original_title: String?,
    val poster_path: String?
)
