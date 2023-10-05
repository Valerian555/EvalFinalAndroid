package com.technipixl.evalfinaleandroid

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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
                        .placeholder(R.drawable.no_image_placeholder)
                        .error(R.drawable.no_image_placeholder)
                        .into(imageView)
        }

        fun isNetworkAvailable(context: Context): Boolean {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val currentNetwork = connectivityManager.activeNetwork
                val networkCapabilities =
                        connectivityManager.getNetworkCapabilities(currentNetwork)

                return networkCapabilities?.run {
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
        }

        fun showToast(context: Context) {
                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show()
        }
}