package com.eclipse.bot.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.databinding.ActivityStartBinding
import com.eclipse.bot.ui.main.MainActivity
import com.eclipse.bot.util.LanguageUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ctx: Context = this

        launch {
            delay(2000)

            val preferences = PreferencesHelper.getSharedPreferences(ctx)

            val lang: String = preferences.getString("lang", "en")!!
            LanguageUtil.changeLanguage(ctx, resources, lang)

            if (preferences.getBoolean("isAuthenticated", false)) {
                startActivity(Intent(ctx, MainActivity::class.java))
                finish()
                return@launch
            }

            startActivity(Intent(ctx, SignInActivity::class.java))
            finish()
        }
    }
}