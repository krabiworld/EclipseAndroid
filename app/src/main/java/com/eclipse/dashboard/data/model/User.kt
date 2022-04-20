package com.eclipse.dashboard.data.model

data class User(
	val id: String,
	val username: String,
	val discriminator: String,
	val avatar: String?,
	val banner: String?,
	val accent_color: Int?)
