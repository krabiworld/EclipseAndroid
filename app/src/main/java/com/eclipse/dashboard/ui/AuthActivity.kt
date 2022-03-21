package com.eclipse.dashboard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.ui.home.HomeActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessToken: String = intent.data?.getQueryParameter("token")!!

		val preferences = PreferencesHelper.get(this)
        val encryptedPreferences = PreferencesHelper.getEncrypted(this)
		preferences.edit()
			.putBoolean("isAuthenticated", true)
			.apply()
        encryptedPreferences.edit()
            .putString("token", accessToken)
            .apply()

        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
