package com.eclipse.dashboard.data.enums

import com.eclipse.dashboard.R

enum class Theme(val index: Int, val resId: Int, val value: String) {
	LIGHT(0, R.string.theme_light, "light"),
	DARK(1, R.string.theme_dark, "dark"),
	DEFAULT(2, R.string.theme_system_default, "default")
}
