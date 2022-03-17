package com.eclipse.bot.data.model

data class User(
	val id: String,
	val username: String,
	val discriminator: String,
	val avatar: String?)
