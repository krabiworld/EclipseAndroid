package com.eclipse.dashboard.util

import android.net.Uri

private const val clientId: String = "920294906008834058"
private const val redirectUri: String = "http://192.168.230.107:8080/api/auth/callback"
private const val scope: String = "identify guilds"
private const val prompt: String = "none"

fun getAuthUri(): Uri {
	return Uri.Builder()
		.scheme("https")
		.authority("discord.com")
		.appendPath("oauth2")
		.appendPath("authorize")
		.appendQueryParameter("response_type", "code")
		.appendQueryParameter("client_id", clientId)
		.appendQueryParameter("redirect_uri", redirectUri)
		.appendQueryParameter("scope", scope)
		.appendQueryParameter("prompt", prompt)
		.build()
}

fun getAvatarUri(type: String, id: String, icon: String, size: Int = 512): Uri {
	return Uri.Builder()
		.scheme("https")
		.authority("cdn.discordapp.com")
		.appendPath(type)
		.appendPath(id)
		.appendPath("$icon.png")
		.appendQueryParameter("size", size.toString())
		.build()
}
