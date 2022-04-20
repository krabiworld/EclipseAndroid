package com.eclipse.dashboard.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.eclipse.dashboard.BuildConfig
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.local.URI_DISCORD
import com.eclipse.dashboard.data.local.URI_DONATIONALERTS
import com.eclipse.dashboard.data.local.URI_GITHUB
import com.eclipse.dashboard.databinding.ActivityAboutBinding
import com.eclipse.dashboard.util.changeLocale
import com.eclipse.dashboard.util.launchUri
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class AboutActivity : AppCompatActivity() {
	private lateinit var binding: ActivityAboutBinding

	override fun attachBaseContext(newBase: Context?) {
		super.attachBaseContext(changeLocale(newBase!!))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityAboutBinding.inflate(layoutInflater)
		setContentView(binding.root)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = getString(R.string.title_about)

		binding.version.text = getString(R.string.version, BuildConfig.VERSION_NAME)

		OssLicensesMenuActivity.setActivityTitle(getString(R.string.oss_licenses))
		val ossLicenses = Intent(this, OssLicensesMenuActivity::class.java)

		val buttonsMap: Map<Button, () -> Unit> = mapOf(
			binding.buttonDiscord to { launchUri(context = this, uriString = URI_DISCORD) },
			binding.buttonDonationAlerts to { launchUri(context = this, uriString = URI_DONATIONALERTS) },
			binding.buttonGithub to { launchUri(context = this, uriString = URI_GITHUB) },
			binding.buttonOssLicenses to { startActivity(ossLicenses) }
		)

		buttonsMap.forEach { (button, onClick) ->
			button.setOnClickListener {
				onClick.invoke()
			}
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		finish()
		return true
	}
}
