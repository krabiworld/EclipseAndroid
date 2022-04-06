package com.eclipse.dashboard.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.databinding.DialogHomeBinding
import com.eclipse.dashboard.ui.AboutActivity
import com.eclipse.dashboard.ui.home.viewmodel.HomeViewModel
import com.eclipse.dashboard.ui.settings.SettingsActivity
import com.eclipse.dashboard.util.getUserAvatar
import com.eclipse.dashboard.util.isNetworkUnavailable
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeDialogFragment : DialogFragment() {
	private var _binding: DialogHomeBinding? = null
	private val binding get() = _binding!!
	private val homeViewModel: HomeViewModel by viewModels(ownerProducer = { requireActivity() })

	override fun onStart() {
		super.onStart()
		requireDialog().window?.apply {
			setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
			setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = DialogHomeBinding.inflate(inflater, container, false)

		homeViewModel.getUser().observe(this) { user ->
			if (user != null) {
				if (user.avatar != null) {
					val avatar = getUserAvatar(user.id, user.avatar, 512)
					val avatarView = binding.userAvatar
					Glide.with(this).load(avatar).into(avatarView)
					avatarView.visibility = View.VISIBLE
				}
				binding.userNickname.text = user.username
				binding.userDiscriminator.text = getString(R.string.user_discriminator, user.discriminator)
				binding.userId.text = user.id
			}
		}

		val signOut = signOutDialog()
		val settings = Intent(context, SettingsActivity::class.java)
		val about = Intent(context, AboutActivity::class.java)

		binding.buttonClose.setOnClickListener {
			requireDialog().dismiss()
		}
		binding.buttonSignOut.setOnClickListener {
			signOut.show()
		}
		binding.buttonSettings.setOnClickListener {
			startActivity(settings)
		}
		binding.buttonAbout.setOnClickListener {
			startActivity(about)
		}

		return binding.root
	}

	override fun onResume() {
		super.onResume()
		if (!isNetworkUnavailable(requireContext())) {
			homeViewModel.updateUserAsync()
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun signOutDialog(): MaterialAlertDialogBuilder {
		val ctx = requireContext()
        return MaterialAlertDialogBuilder(ctx)
            .setMessage(getString(R.string.message_logout))
            .setNegativeButton(getString(R.string.button_cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton(getString(R.string.button_ok)) { _, _ ->
                PreferencesHelper.signOut(ctx, requireActivity())
            }
    }

	companion object {
		const val TAG = "HomeDialogFragment"
	}
}
