package com.eclipse.dashboard.ui.home

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.data.local.Token
import com.eclipse.dashboard.databinding.ActivityHomeBinding
import com.eclipse.dashboard.ui.home.viewmodel.HomeViewModel
import com.eclipse.dashboard.ui.settings.SettingsFragment
import com.eclipse.dashboard.util.changeLocale
import com.eclipse.dashboard.util.getUserAvatar

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

	override fun attachBaseContext(newBase: Context?) {
		super.attachBaseContext(changeLocale(newBase!!))
	}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

		Token = PreferencesHelper.getEncrypted(this).getString("token", "")!!

		val homeViewModel: HomeViewModel by viewModels()
		val dialog = HomeDialogFragment()
		val avatar = binding.userAvatar
        val toolbar = binding.toolbar
        val navView = binding.navView
        val navController = binding.navHostFragment.getFragment<NavHostFragment>().navController
        val appBarConfiguration = AppBarConfiguration(navView.menu)

        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

		homeViewModel.getUser().observe(this) { user ->
			if (homeViewModel.tokenIsCorrupted) {
				PreferencesHelper.signOut(this, this)
			}

			if (user?.avatar == null) return@observe

			// load avatar
			Glide.with(this)
				.load(getUserAvatar(user.id, user.avatar))
				.circleCrop()
				.into(avatar)
		}

		// on click - show main dialog
		avatar.setOnClickListener {
			if (dialog.isAdded) return@setOnClickListener
			dialog.show(supportFragmentManager, HomeDialogFragment.TAG)
        }
    }

	override fun onResume() {
		super.onResume()
		if (SettingsFragment.languageIsChanged) {
			SettingsFragment.languageIsChanged = false
			recreate()
		}
	}
}
