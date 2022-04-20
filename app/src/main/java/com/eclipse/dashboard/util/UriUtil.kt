package com.eclipse.dashboard.util

import android.net.Uri
import com.eclipse.dashboard.data.local.*

const val discordImageBaseUrl = "https://cdn.discordapp.com"

fun getAuthUri(): Uri {
	return Uri.Builder()
		.scheme("https")
		.authority("discord.com")
		.appendPath("oauth2")
		.appendPath("authorize")
		.appendQueryParameter("response_type", "code")
		.appendQueryParameter("client_id", AUTH_CLIENT_ID)
		.appendQueryParameter("redirect_uri", AUTH_REDIRECT_URI)
		.appendQueryParameter("scope", AUTH_SCOPE)
		.appendQueryParameter("prompt", AUTH_PROMPT)
		.build()
}

fun getGuildIcon(id: String, icon: String, size: Int = 48): String {
	return "$discordImageBaseUrl/icons/$id/$icon.png?size=$size"
}

fun getUserAvatar(id: String, avatar: String, size: Int = 48): String {
	return "$discordImageBaseUrl/avatars/$id/$avatar.png?size=$size"
}

fun getUserBanner(id: String, banner: String, size: Int = 512): String {
	return "$discordImageBaseUrl/banners/$id/$banner.png?size=$size"
}
