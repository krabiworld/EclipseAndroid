package com.eclipse.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.eclipse.dashboard.data.local.githubUri
import com.eclipse.dashboard.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
	private var _binding: FragmentAboutBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentAboutBinding.inflate(inflater, container, false)

		val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
		val customTabsIntent: CustomTabsIntent = builder.build()

		binding.buttonGithub.setOnClickListener {
			customTabsIntent.launchUrl(context!!, githubUri)
		}

		return binding.root
    }

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
