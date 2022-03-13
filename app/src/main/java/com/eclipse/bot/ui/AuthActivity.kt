package com.eclipse.bot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.ui.home.MainActivity

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

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
