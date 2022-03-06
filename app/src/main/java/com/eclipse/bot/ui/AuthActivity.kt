package com.eclipse.bot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.ui.main.MainActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessToken: String = intent.data?.getQueryParameter("token")!!

        val sharedPreferences = PreferencesHelper.getSharedPreferences(this)
        sharedPreferences.edit()
            .putString("token", accessToken)
            .putBoolean("isAuthenticated", true)
            .apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}