package com.eclipse.bot.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.ui.main.MainActivity
import com.eclipse.bot.util.AuthUtil
import com.eclipse.bot.util.DialogUtil
import com.eclipse.bot.util.LanguageUtil
import com.eclipse.bot.util.NetworkUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class StartActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang: String = PreferencesHelper.getSharedPreferences(this).getString("lang", "en")!!
        LanguageUtil.changeLanguage(this, resources, lang)


        val sharedPreferences = PreferencesHelper.getSharedPreferences(this)
        if (sharedPreferences.getBoolean("isAuthenticated", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        val dialog = DialogUtil.networkUnavailableDialog(this)
        if (!NetworkUtil.isNetworkAvailable(this)) {
            dialog.show()
        }

        setContentView(R.layout.activity_start)

        findViewById<Button>(R.id.aboutButton).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        findViewById<Button>(R.id.signInButton).setOnClickListener {
            val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(AuthUtil.getAuthUrl()))
        }
    }
}