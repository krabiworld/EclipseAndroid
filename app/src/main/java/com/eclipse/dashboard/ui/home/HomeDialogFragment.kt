package com.eclipse.dashboard.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.eclipse.dashboard.util.getUserBanner
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
			if (user == null) return@observe

			// if banner not null - load banner and change visibility imageview to visible
			if (user.banner != null) {
				Glide.with(this)
					.load(getUserBanner(user.id, user.banner))
					.into(binding.banner.apply { visibility = View.VISIBLE })
			}

			// if avatar not null - load avatar and change visibility imageview to visible
			if (user.avatar != null) {
				Glide.with(this)
					.load(getUserAvatar(user.id, user.avatar))
					.circleCrop()
					.into(binding.avatar.apply { visibility = View.VISIBLE })
			}

			binding.nickname.text = user.username
			binding.discriminator.text = getString(R.string.user_discriminator, user.discriminator)
			binding.id.text = user.id
		}

		val signOut = signOutDialog()
		val settings = Intent(context, SettingsActivity::class.java)
		val about = Intent(context, AboutActivity::class.java)

		val buttons: Map<Button, () -> Unit> = mapOf(
			binding.buttonSettings to { startActivity(settings) },
			binding.buttonAbout to { startActivity(about) }
		)

		binding.buttonClose.setOnClickListener {
			requireDialog().dismiss()
		}
		binding.buttonSignOut.setOnClickListener {
			signOut.show()
		}
		buttons.forEach { (button, action) ->
			button.setOnClickListener {
				action.invoke()
				requireDialog().dismiss()
			}
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

	// sign out from account dialog
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
