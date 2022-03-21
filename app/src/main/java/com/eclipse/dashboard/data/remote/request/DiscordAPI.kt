package com.eclipse.dashboard.data.remote.request

import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DiscordAPI {
	@GET("users/@me")
	fun fetchUser(@Header("Authorization") authorization: String): Call<User>

	@GET("users/@me/guilds")
	fun fetchGuilds(@Header("Authorization") authorization: String): Call<ArrayList<Guild>>
}
