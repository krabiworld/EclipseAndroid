package com.eclipse.bot.data.remote.request

import com.eclipse.bot.data.model.Guild
import com.eclipse.bot.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DiscordService {
	@GET("users/@me")
	fun fetchUser(@Header("Authorization") authorization: String): Call<User>

	@GET("users/@me/guilds")
	fun fetchGuilds(@Header("Authorization") authorization: String): Call<ArrayList<Guild>>
}
