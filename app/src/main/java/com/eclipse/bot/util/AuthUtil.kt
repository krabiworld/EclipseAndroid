package com.eclipse.bot.util

import android.net.Uri

class AuthUtil {
    companion object {
		private const val clientId: String = "920294906008834058"
        private const val redirectUri: String = "http://192.168.230.107:8080/api/auth/callback"
        private const val scope: String = "identify guilds"
		private const val prompt: String = "none"

        fun getAuthUrl(): String {
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
				.toString()
        }
    }
}
