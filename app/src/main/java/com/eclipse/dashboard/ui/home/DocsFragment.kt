package com.eclipse.dashboard.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.eclipse.dashboard.databinding.FragmentDocsBinding

class DocsFragment : Fragment() {
	private var _binding: FragmentDocsBinding? = null
	private val binding get() = _binding!!

	@SuppressLint("SetJavaScriptEnabled")
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentDocsBinding.inflate(inflater, container, false)

		binding.webView.apply {
			webViewClient = WebViewClient()
			settings.apply {
				javaScriptEnabled = true
				builtInZoomControls = true
				displayZoomControls = false
			}
			loadUrl("https://docs.juniper.bot/") // Surprise
		}

		return binding.root
    }

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
