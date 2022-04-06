package com.eclipse.dashboard.ui.server.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eclipse.dashboard.databinding.FragmentGeneralBinding

class GeneralFragment : Fragment() {
	private var _binding: FragmentGeneralBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentGeneralBinding.inflate(inflater, container, false)

		val autoGreetingsDialog = AutoGreetingsBottomSheet()

		binding.autoGreetings.button.setOnClickListener {
			if (autoGreetingsDialog.isAdded) return@setOnClickListener
			autoGreetingsDialog.show(requireActivity().supportFragmentManager, AutoGreetingsBottomSheet.TAG)
		}
		binding.autoGreetings.switcher.setOnCheckedChangeListener { _, checked ->
			binding.autoGreetings.button.isEnabled = checked
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}
