package com.eclipse.bot.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.eclipse.bot.data.enum.Theme
import com.eclipse.bot.data.local.PreferencesHelper

class ThemeUtil {
	companion object {
		fun changeTheme(theme: String) {
			var mode: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
			when (theme) {
				"light" -> mode = AppCompatDelegate.MODE_NIGHT_NO
				"dark" -> mode = AppCompatDelegate.MODE_NIGHT_YES
			}
			AppCompatDelegate.setDefaultNightMode(mode)
		}

		fun currentTheme(context: Context?): Theme {
			var theme = Theme.DEFAULT

			if (PreferencesHelper.get(context).getString("theme", "default") == "default") {
				return theme
			}

			when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
				Configuration.UI_MODE_NIGHT_YES -> theme = Theme.DARK
				Configuration.UI_MODE_NIGHT_NO -> theme = Theme.LIGHT
			}
			return theme
		}
	}
}
