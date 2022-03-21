package com.eclipse.dashboard.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.R

class SettingsActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		supportActionBar?.title = getString(R.string.title_settings)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		supportFragmentManager
			.beginTransaction()
			.add(android.R.id.content, SettingsFragment())
			.commit()
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		finish()
		return true
	}
}
