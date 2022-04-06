package com.eclipse.dashboard.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.enums.Language
import com.eclipse.dashboard.data.enums.Theme
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.util.changeTheme
import com.eclipse.dashboard.util.currentLanguage
import com.eclipse.dashboard.util.currentTheme

class SettingsFragment : PreferenceFragmentCompat() {
	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference, rootKey)

		val preferences = PreferencesHelper.get(requireContext())

		// get current theme and language
		val currentTheme = currentTheme(requireContext(), preferences)
		val currentLang = currentLanguage(preferences)

		val themePreference: ListPreference = findPreference("theme")!!
		val langPreference: ListPreference = findPreference("lang")!!
		// set current theme and language in selected item
		themePreference.setValueIndex(currentTheme.index)
		langPreference.setValueIndex(currentLang.index)
		// set current theme and language in summary
		themePreference.summary = getString(currentTheme.resId)
		langPreference.summary = getString(currentLang.resId)

		themePreference.setOnPreferenceChangeListener { preference, newValue ->
			val newTheme = newValue.toString()
			if (currentTheme.value == newTheme) return@setOnPreferenceChangeListener true
			// update summary
			preference.summary = getString(Theme.valueOf(newTheme.uppercase()).resId)

			// update theme in preferences
			preferences.edit().putString("theme", newTheme).apply()

			// change theme
			changeTheme(newTheme)
			true
		}

		langPreference.setOnPreferenceChangeListener { preference, newValue ->
			val newLang = newValue.toString()
			if (currentLang.value == newLang) return@setOnPreferenceChangeListener true

			languageIsChanged = true

			// update summary
			preference.summary = getString(Language.valueOf(newLang.uppercase()).resId)

			// update language in preferences
			preferences.edit().putString("lang", newLang).apply()

			// recreate activity
			requireActivity().recreate()
//			val intent = Intent(requireContext(), HomeActivity::class.java)
//			intent.addFlags(
//				Intent.FLAG_ACTIVITY_CLEAR_TOP or
//				Intent.FLAG_ACTIVITY_CLEAR_TASK or
//				Intent.FLAG_ACTIVITY_NEW_TASK)
//			startActivity(intent)
			true
		}
	}

	companion object {
		var languageIsChanged = false
	}
}
