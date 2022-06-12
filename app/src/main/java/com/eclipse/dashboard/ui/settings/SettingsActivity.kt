package com.eclipse.dashboard.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.R
import com.eclipse.dashboard.databinding.ActivitySettingsBinding
import com.eclipse.dashboard.util.changeLanguage

class SettingsActivity : AppCompatActivity() {
	private lateinit var binding: ActivitySettingsBinding

	override fun attachBaseContext(newBase: Context?) {
		super.attachBaseContext(changeLanguage(newBase!!))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivitySettingsBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = getString(R.string.title_settings)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		finish()
		return true
	}
}
