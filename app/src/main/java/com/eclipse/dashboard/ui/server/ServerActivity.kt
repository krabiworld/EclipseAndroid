package com.eclipse.dashboard.ui.server

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.R
import com.eclipse.dashboard.databinding.ActivityServerBinding
import com.eclipse.dashboard.ui.server.adapter.ViewPager2Adapter
import com.eclipse.dashboard.util.changeLocale
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class ServerActivity : AppCompatActivity() {
	private lateinit var binding: ActivityServerBinding

	override fun attachBaseContext(newBase: Context?) {
		super.attachBaseContext(changeLocale(newBase!!))
	}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

		binding = ActivityServerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras: Bundle = intent.extras!!
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = extras.getString("guildName")

		val tabs = mapOf(
			0 to R.string.tab_general,
			1 to R.string.tab_private_rooms,
			2 to R.string.tab_audit,
			3 to R.string.tab_leaders,
			4 to R.string.tab_auto_moderation,
			5 to R.string.tab_custom_commands
		)

		binding.viewPager2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
		TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
			tabs[position]?.let { tab.setText(it) }
		}.attach()
    }

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.server_menu, menu)
		return true
	}

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.action_saveButton -> {
			Snackbar.make(findViewById(android.R.id.content), R.string.saved, Snackbar.LENGTH_SHORT).show()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
