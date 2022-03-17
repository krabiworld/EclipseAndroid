package com.eclipse.bot.data.model

data class Guild(
	val id: String,
	val name: String,
	val icon: String?,
	val owner: Boolean,
	val permissions: Long)
