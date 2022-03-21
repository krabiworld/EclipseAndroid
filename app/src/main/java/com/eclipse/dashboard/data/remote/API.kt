package com.eclipse.dashboard.data.remote

import com.eclipse.dashboard.data.remote.request.DiscordAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val discordAPI: DiscordAPI by lazy {
	Retrofit.Builder()
		.baseUrl("https://discord.com/api/")
		.addConverterFactory(GsonConverterFactory.create())
		.build()
		.create(DiscordAPI::class.java)
}
