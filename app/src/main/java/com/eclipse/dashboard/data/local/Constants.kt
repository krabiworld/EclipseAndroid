package com.eclipse.dashboard.data.local

var Token = ""

object Uris {
	const val GITHUB: String = "https://github.com/Night-Devs/"
	const val DISCORD: String = "https://discord.gg/PHuvYMrvdr"
	const val DONATIONALERTS: String = "https://www.donationalerts.com/r/electroplayer"
}

object AuthData {
	const val CLIENT_ID: String = "920294906008834058"
	const val REDIRECT_URI: String = "https://eclipsebackend.herokuapp.com/auth/callback/mobile"
	const val SCOPE: String = "identify guilds"
	const val PROMPT: String = "none"
}
