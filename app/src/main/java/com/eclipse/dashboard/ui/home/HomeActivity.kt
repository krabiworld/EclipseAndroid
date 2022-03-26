package com.eclipse.dashboard.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.databinding.ActivityHomeBinding
import com.eclipse.dashboard.ui.StartActivity
import com.eclipse.dashboard.ui.home.model.HomeViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = binding.userAvatar
        val toolbar = binding.toolbar
        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(navView.menu)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // if token is empty, get and write token to variable
        if (homeViewModel.token.isEmpty()) {
            homeViewModel.token = PreferencesHelper.getEncrypted(this).getString("token", "")!!
        }

        homeViewModel.getAvatar().observe(this) { avatar ->
            if (avatar.isNotEmpty()) {
                Glide.with(this).load(avatar).circleCrop().into(image)
            }
        }

        // on click - show logout dialog
        image.setOnClickListener {
            logoutDialog().show()
        }

        // on long click - update avatar
        image.setOnLongClickListener {
            homeViewModel.updateAvatarAsync()
            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun logoutDialog(): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.message_logout))
            .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.button_logout)) { _, _ ->
                val preferences = PreferencesHelper.get(this)
                val encryptedPreferences = PreferencesHelper.getEncrypted(this)
                preferences.edit().putBoolean("isAuthenticated", false).apply()
                encryptedPreferences.edit().putString("token", "").apply()

                startActivity(Intent(this, StartActivity::class.java))
                finish()
            }
    }
}
