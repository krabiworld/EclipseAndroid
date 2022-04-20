package com.eclipse.dashboard.data.remote.request

import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.data.model.User
import com.eclipse.dashboard.util.getHeaderToken
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DiscordAPI {
	@GET("users/@me")
	fun fetchUser(@Header("Authorization") authorization: String = getHeaderToken()): Call<User>

	@GET("users/@me/guilds")
	fun fetchGuilds(@Header("Authorization") authorization: String = getHeaderToken()): Call<ArrayList<Guild>>
}
