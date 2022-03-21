package com.eclipse.dashboard.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.eclipse.dashboard.data.enums.Theme
import com.eclipse.dashboard.data.local.PreferencesHelper

// if variable theme not equal "light" or "dark" will be theme set to follow system
fun changeTheme(theme: String) {
	val mode = when (theme) {
		"light" -> AppCompatDelegate.MODE_NIGHT_NO
		"dark" -> AppCompatDelegate.MODE_NIGHT_YES
		else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
	}
	// set new theme in whole app
	AppCompatDelegate.setDefaultNightMode(mode)
}

fun currentTheme(context: Context?): Theme {
	// if in preferences theme == default returns system default theme
	if (PreferencesHelper.get(context).getString("theme", "default") == "default") {
		return Theme.DEFAULT
	}

	// because here no ui mode "follow system" or "system default"
	return when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
		Configuration.UI_MODE_NIGHT_NO -> Theme.LIGHT
		Configuration.UI_MODE_NIGHT_YES -> Theme.DARK
		else -> Theme.DEFAULT
	}
}
