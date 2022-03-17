package com.eclipse.bot.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.eclipse.bot.R
import com.eclipse.bot.data.enum.Theme
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.util.ThemeUtil

class SettingsFragment : PreferenceFragmentCompat() {
	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference, rootKey)

		val preferences = PreferencesHelper.get(context)

		val currentTheme = ThemeUtil.currentTheme(context)

		val themePreference: ListPreference? = findPreference("theme")
		themePreference?.setValueIndex(currentTheme.index)
		themePreference?.summary = getString(currentTheme.id)

		themePreference?.setOnPreferenceChangeListener { preference, newValue ->
			val theme = newValue.toString()
			preference.summary = getString(Theme.valueOf(theme.uppercase()).id)

			preferences.edit().putString("theme", theme).apply()
			ThemeUtil.changeTheme(theme)
			true
		}
    }
}
