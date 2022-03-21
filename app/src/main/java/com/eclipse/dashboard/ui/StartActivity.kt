package com.eclipse.dashboard.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.ui.home.HomeActivity
import com.eclipse.dashboard.util.changeTheme
import com.eclipse.dashboard.util.currentTheme

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val preferences = PreferencesHelper.get(this)

		val theme: String = preferences.getString("theme", "default")!!
		if (currentTheme(this).value != theme) {
			changeTheme(theme)
		}

		if (preferences.getBoolean("isAuthenticated", false)) {
			startActivity(Intent(this, HomeActivity::class.java))
		} else {
			startActivity(Intent(this, SignInActivity::class.java))
		}
		finish()
	}
}
