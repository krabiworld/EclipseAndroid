package com.eclipse.bot.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkUtil {
    companion object {
        // Don't remove this function yet for creating requests to future API
        //val retrofit: Retrofit = Retrofit.Builder()
        //    .baseUrl("https://host:port/api/")
        //    .addConverterFactory(GsonConverterFactory.create())
        //    .build()

        fun isNetworkAvailable(context: Context?): Boolean {
            val connectivityManager: ConnectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }

            return false
        }
    }
}
