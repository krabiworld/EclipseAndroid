package com.eclipse.dashboard.util

import com.eclipse.dashboard.data.local.Token

fun getHeaderToken(): String {
	return "Bearer $Token"
}
