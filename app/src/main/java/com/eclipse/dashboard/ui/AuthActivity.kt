package com.eclipse.dashboard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eclipse.dashboard.data.local.AUTH_KEY
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.data.local.TOKEN_KEY
import com.eclipse.dashboard.ui.home.HomeActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessToken: String = intent.data?.getQueryParameter("token")!!

		val preferences = PreferencesHelper.get(this)
        val encryptedPreferences = PreferencesHelper.getEncrypted(this)
		preferences.edit()
			.putBoolean(AUTH_KEY, true)
			.apply()
        encryptedPreferences.edit()
            .putString(TOKEN_KEY, accessToken)
            .apply()

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
