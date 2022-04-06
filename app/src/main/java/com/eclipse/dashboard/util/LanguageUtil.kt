package com.eclipse.dashboard.util

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import com.eclipse.dashboard.data.enums.Language
import com.eclipse.dashboard.data.local.PreferencesHelper
import java.util.*

fun changeLocale(context: Context): Context {
	val lang = PreferencesHelper.get(context).getString("lang", "en")!!
	val locale = Locale(lang)
	Locale.setDefault(locale)
	val configuration = context.resources.configuration
	configuration.setLocale(locale)
	return ContextWrapper(context.createConfigurationContext(configuration))
}

fun currentLanguage(preferences: SharedPreferences): Language {
	return Language.valueOf(preferences.getString("lang", "en")!!.uppercase())
}
