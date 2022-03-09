package com.eclipse.bot.util

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.eclipse.bot.data.local.PreferencesHelper

class ThemeUtil {
	companion object {
		fun changeTheme(context: Context?, isDark: Boolean) {
			val preferences = PreferencesHelper.getSharedPreferences(context!!)
			if (isDark) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				preferences.edit().putBoolean("isDark", true).apply()
				return
			}
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
		}

		fun isDark(context: Context?): Boolean {
			var isDark = false
			when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
				Configuration.UI_MODE_NIGHT_YES -> isDark = true
			}
			return isDark
		}
	}
}
