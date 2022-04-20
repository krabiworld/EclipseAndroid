package com.eclipse.dashboard.data.model

import com.google.gson.annotations.SerializedName

data class Guild(
	val id: String,
	val name: String,
	val icon: String?,
	val owner: Boolean,
	val permissions: Long,
	@SerializedName("member_count")
	val memberCount: Int)
