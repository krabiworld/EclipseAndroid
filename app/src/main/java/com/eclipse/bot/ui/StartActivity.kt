package com.eclipse.bot.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.ui.home.HomeActivity
import com.eclipse.bot.util.ThemeUtil

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val preferences = PreferencesHelper.get(this)

		val theme: String = preferences.getString("theme", "default")!!
		if (ThemeUtil.currentTheme(this).value != theme) {
			ThemeUtil.changeTheme(theme)
		}

		if (preferences.getBoolean("isAuthenticated", false)) {
			startActivity(Intent(this, HomeActivity::class.java))
		} else {
			startActivity(Intent(this, SignInActivity::class.java))
		}
		finish()
	}
}
