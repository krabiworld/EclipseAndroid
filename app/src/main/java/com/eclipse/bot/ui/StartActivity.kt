package com.eclipse.bot.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.databinding.ActivityStartBinding
import com.eclipse.bot.ui.home.MainActivity
import com.eclipse.bot.util.LanguageUtil
import com.eclipse.bot.util.ThemeUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private lateinit var binding: ActivityStartBinding

	private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
		val ctx: Context = this
		val preferences = PreferencesHelper.getSharedPreferences(ctx)
		val isDark = preferences.getBoolean("isDark", true)

		if (isDark and !ThemeUtil.isDark(ctx)) {
			ThemeUtil.changeTheme(ctx, true)
		}

		super.onCreate(savedInstanceState)

		binding = ActivityStartBinding.inflate(layoutInflater)
		firebaseAnalytics = Firebase.analytics
		setContentView(binding.root)

        launch {
            delay(2000)

            val lang: String = preferences.getString("lang", "en")!!
            LanguageUtil.changeLanguage(ctx, resources, lang)

			val bundle = Bundle()
			bundle.putString("language", lang)
			firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)

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
