package com.eclipse.bot.util

class AuthUtil {
    companion object {
        private const val clientId: String = "920294906008834058"
        private const val redirectUri: String = "http://192.168.48.107:8080/api/auth/callback"
        private const val scope: String = "identify guilds guilds.join"

        fun getAuthUrl(): String {
            return "https://discord.com/oauth2/authorize?response_type=code&client_id=$clientId&redirect_uri=$redirectUri&scope=$scope"
        }
    }
}