package com.eclipse.dashboard.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.data.local.AUTH_KEY
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.data.local.THEME_KEY
import com.eclipse.dashboard.ui.home.HomeActivity
import com.eclipse.dashboard.util.changeTheme
import com.eclipse.dashboard.util.currentTheme

class StartActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val preferences = PreferencesHelper.get(this)

		val theme: String = preferences.getString(THEME_KEY, "default")!!
		if (currentTheme(this, preferences).value != theme) {
			changeTheme(theme)
		}

		if (preferences.getBoolean(AUTH_KEY, false)) {
			startActivity(Intent(this, HomeActivity::class.java))
		} else {
			startActivity(Intent(this, SignInActivity::class.java))
		}
		finish()
	}
}
