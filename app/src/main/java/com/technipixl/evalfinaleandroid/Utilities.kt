package com.technipixl.evalfinaleandroid

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.technipixl.evalfinaleandroid.network.service.MovieServiceImpl
import kotlin.math.roundToInt

object Utilities {
        const val API_KEY: String = "55530312075972a425f5fa13e21b218f"
        private val movieServiceImpl by lazy { MovieServiceImpl() }

        fun getMovieService(): MovieServiceImpl {
                return movieServiceImpl
        }

        fun roundFloatToString(number: Float): Float {
                return (number * 10.0f).roundToInt() / 10.0f
        }

        fun setupImage(url: String, imageView: ImageView) {
                val baseUrlImage = "https://image.tmdb.org/t/p/w500"
                Picasso.get()
                        .load("$baseUrlImage$url")
                        .fit()
                        .centerCrop()
                        .into(imageView)
        }
}