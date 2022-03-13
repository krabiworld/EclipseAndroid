package com.eclipse.bot.data.enum

import com.eclipse.bot.R

enum class Theme(val index: Int, val id: Int, val value: String) {
	LIGHT(0, R.string.theme_light, "light"),
	DARK(1, R.string.theme_dark, "dark"),
	DEFAULT(2, R.string.theme_system_default, "default")
}
