package com.eclipse.bot.util

import android.content.Context
import android.content.res.Resources
import com.eclipse.bot.data.local.PreferencesHelper
import java.util.*

class LanguageUtil {
    companion object {
        fun changeLanguage(context: Context?, resources: Resources, language: String) {
            val newLocale = Locale(language)
            val displayMetrics = resources.displayMetrics
            val config = resources.configuration
            config.setLocale(newLocale)
            resources.updateConfiguration(config, displayMetrics)

            PreferencesHelper.getSharedPreferences(context!!).edit().putString("lang", language).apply()
        }
    }
}
