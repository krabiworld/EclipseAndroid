package com.eclipse.dashboard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// check internet connection, true if network unavailable and false if available
fun isNetworkUnavailable(context: Context): Boolean {
	val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)

	if (capabilities != null) {
		return when {
			capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> false
			capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> false
			capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> false
			else -> true
		}
	}

	return true
}
