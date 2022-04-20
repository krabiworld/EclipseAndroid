package com.eclipse.dashboard.ui.server.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eclipse.dashboard.R
import com.eclipse.dashboard.databinding.BottomSheetAutoGreetingsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AutoGreetingsBottomSheet : BottomSheetDialogFragment() {
	private var _binding: BottomSheetAutoGreetingsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = BottomSheetAutoGreetingsBinding.inflate(inflater, container, false)

		isCancelable = false

		val toolbar = binding.appBar.toolbar
		toolbar.inflateMenu(R.menu.server_menu)
		toolbar.setOnMenuItemClickListener {
			true
		}
		toolbar.setNavigationOnClickListener {
			dismiss()
		}

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	companion object {
		const val TAG = "AutoGreetingsBottomSheet"
	}
}
