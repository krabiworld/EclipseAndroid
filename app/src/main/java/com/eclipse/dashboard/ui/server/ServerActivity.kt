package com.eclipse.dashboard.ui.server

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.R
import com.eclipse.dashboard.databinding.ActivityServerBinding
import com.eclipse.dashboard.ui.server.adapter.ViewPager2Adapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class ServerActivity : AppCompatActivity() {
	private lateinit var binding: ActivityServerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

		binding = ActivityServerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val extras: Bundle = intent.extras!!
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = extras.getString("guildName")
        supportActionBar?.subtitle = extras.getString("guildId")

		binding.viewPager2.adapter = ViewPager2Adapter(supportFragmentManager, lifecycle)
		TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
			tab.text = when (position) {
				0 -> getString(R.string.tab_general)
				else -> throw IllegalStateException()
			}
		}.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
