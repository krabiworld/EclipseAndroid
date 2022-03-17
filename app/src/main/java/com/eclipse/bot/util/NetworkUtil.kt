package com.eclipse.bot.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.eclipse.bot.data.remote.request.DiscordService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtil {
    companion object {
        private val discordRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://discord.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

		val discordService: DiscordService = discordRetrofit.create(DiscordService::class.java)

		// check internet connection, true if network available and false if unavailable
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
