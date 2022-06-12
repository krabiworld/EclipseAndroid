package com.eclipse.dashboard.data.remote.request

import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.data.model.User
import com.eclipse.dashboard.util.getBearerToken
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface DiscordAPI {
	@GET("users/@me")
	fun fetchUser(@Header("Authorization") auth: String = getBearerToken()): Call<User>

	@GET("users/@me/guilds")
	fun fetchGuilds(@Header("Authorization") auth: String = getBearerToken()): Call<ArrayList<Guild>>
}
