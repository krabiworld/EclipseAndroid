package com.eclipse.dashboard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.eclipse.dashboard.R
import com.google.android.material.snackbar.Snackbar

// check internet connection, true if network available and false if unavailable
private fun isNetworkAvailable(context: Context?): Boolean {
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

// if network unavailable shows snackbar and returns true
fun checkNetworkConnection(context: Context?, coordinatorLayout: CoordinatorLayout): Boolean {
	if (isNetworkAvailable(context)) return false

	Snackbar.make(coordinatorLayout, R.string.network_unavailable, Snackbar.LENGTH_SHORT).show()

	return true
}
