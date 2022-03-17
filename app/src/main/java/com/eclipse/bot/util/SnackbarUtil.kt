package com.eclipse.bot.util

import android.app.Activity
import android.content.Context
import android.view.View
import com.eclipse.bot.R
import com.google.android.material.snackbar.Snackbar

class SnackbarUtil {
	companion object {
		// if network unavailable returns true and show snackbar
		fun checkNetworkConnection(context: Context?, activity: Activity?): Boolean {
			if (NetworkUtil.isNetworkAvailable(context)) return false

			val content: View = activity?.findViewById(android.R.id.content)!!
			Snackbar.make(content, R.string.network_unavailable, Snackbar.LENGTH_SHORT).show()

			return true
		}

		fun tooManyRequests(activity: Activity?) {
			val content: View = activity?.findViewById(android.R.id.content)!!
			Snackbar.make(content, R.string.too_many_requests, Snackbar.LENGTH_SHORT).show()
		}
	}
}
