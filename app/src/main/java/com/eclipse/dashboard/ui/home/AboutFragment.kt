package com.eclipse.dashboard.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.eclipse.dashboard.BuildConfig
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.local.discordUri
import com.eclipse.dashboard.data.local.donationAlertsUri
import com.eclipse.dashboard.data.local.githubUri
import com.eclipse.dashboard.databinding.FragmentAboutBinding
import com.eclipse.dashboard.util.launchUri

class AboutFragment : Fragment() {
	private var _binding: FragmentAboutBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentAboutBinding.inflate(inflater, container, false)

		binding.version.text = getString(R.string.version, BuildConfig.VERSION_NAME)
		
		val linkList: Map<Button, Uri> = mapOf(
			binding.buttonDiscord to discordUri,
			binding.buttonDonationAlerts to donationAlertsUri,
			binding.buttonGithub to githubUri
		)

		linkList.forEach { (button, uri) ->
			button.setOnClickListener {
				launchUri(context, uri)
			}
		}

		return binding.root
    }

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
