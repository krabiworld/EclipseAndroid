package com.eclipse.dashboard.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.enums.Theme
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.util.changeTheme
import com.eclipse.dashboard.util.currentTheme

class SettingsFragment : PreferenceFragmentCompat() {
	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference, rootKey)

		// get current theme
		val currentTheme = currentTheme(context)

		val themePreference: ListPreference? = findPreference("theme")
		// set current theme in selected item
		themePreference?.setValueIndex(currentTheme.index)
		// set current theme in summary
		themePreference?.summary = getString(currentTheme.resId)

		themePreference?.setOnPreferenceChangeListener { preference, newValue ->
			val newTheme = newValue.toString()
			// update summary
			preference.summary = getString(Theme.valueOf(newTheme.uppercase()).resId)

			// update theme in preferences
			val preferences = PreferencesHelper.get(context)
			preferences.edit().putString("theme", newTheme).apply()

			// change theme
			changeTheme(newTheme)
			true
		}
	}
}
