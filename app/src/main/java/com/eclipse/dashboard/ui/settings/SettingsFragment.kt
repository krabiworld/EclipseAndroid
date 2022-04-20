package com.eclipse.dashboard.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.enums.Language
import com.eclipse.dashboard.data.enums.Theme
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.util.changeTheme
import com.eclipse.dashboard.util.currentLanguage
import com.eclipse.dashboard.util.currentTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

class SettingsFragment : PreferenceFragmentCompat() {
	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference, rootKey)

		val preferences = PreferencesHelper.get(requireContext())
		val firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
		val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

		// get current theme and language
		val currentTheme = currentTheme(requireContext(), preferences)
		val currentLang = currentLanguage(preferences)

		val themePreference: ListPreference = findPreference("theme")!!
		val langPreference: ListPreference = findPreference("lang")!!
		val analyticsPreference: SwitchPreference = findPreference("analytics")!!
		val crashlyticsPreference: SwitchPreference = findPreference("crashlytics")!!

		// set default values
		themePreference.setValueIndex(currentTheme.index)
		langPreference.setValueIndex(currentLang.index)
		analyticsPreference.isChecked = preferences.getBoolean("analytics", true)
		crashlyticsPreference.isChecked = preferences.getBoolean("crashlytics", true)

		// set current theme and language to summary
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
			true
		}

		analyticsPreference.setOnPreferenceChangeListener { _, newValue ->
			val value = newValue as Boolean
			preferences.edit().putBoolean("analytics", value).apply()
			firebaseAnalytics.setAnalyticsCollectionEnabled(value)
			true
		}

		crashlyticsPreference.setOnPreferenceChangeListener { _, newValue ->
			val value = newValue as Boolean
			preferences.edit().putBoolean("crashlytics", value).apply()
			firebaseCrashlytics.setCrashlyticsCollectionEnabled(value)
			true
		}
	}

	companion object {
		var languageIsChanged = false
	}
}
