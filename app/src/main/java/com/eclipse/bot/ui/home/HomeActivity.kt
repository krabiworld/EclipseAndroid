package com.eclipse.bot.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.databinding.ActivityHomeBinding
import com.eclipse.bot.ui.StartActivity
import com.eclipse.bot.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val appBarConfiguration = AppBarConfiguration(setOf(
			R.id.navigation_dashboard, R.id.navigation_profile, R.id.navigation_about
		))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_signOut -> {
				val preferences = PreferencesHelper.get(this)
                val encryptedPreferences = PreferencesHelper.getEncrypted(this)
				preferences.edit().putBoolean("isAuthenticated", false).apply()
                encryptedPreferences.edit().putString("token", "").apply()

                startActivity(Intent(this, StartActivity::class.java))
                finish()
                true
            }
			R.id.option_settings -> {
				startActivity(Intent(this, SettingsActivity::class.java))
				true
			}
            else -> super.onOptionsItemSelected(item)
        }
    }
}
