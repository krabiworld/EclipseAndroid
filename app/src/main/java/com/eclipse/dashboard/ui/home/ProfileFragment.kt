package com.eclipse.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.databinding.FragmentProfileBinding
import com.eclipse.dashboard.ui.home.model.ProfileViewModel
import com.eclipse.dashboard.util.checkNetworkConnection
import com.eclipse.dashboard.util.getAvatarUri

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
	private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
		val root = binding.root

		if (profileViewModel.token.isEmpty()) {
			profileViewModel.token = PreferencesHelper.getEncrypted(context).getString("token", "")!!
		}
		if (profileViewModel.getUser().value == null) {
			binding.swipeToRefresh.isRefreshing = true
		}

		profileViewModel.getUser().observe(this) { user ->
			if (user != null) {
				val image: ImageView = binding.userAvatar
				if (user.avatar == null) {
					image.visibility = View.GONE
				} else {
					val avatar = getAvatarUri("avatars", user.id, user.avatar)
					image.setOnClickListener {
						val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
						val customTabsIntent: CustomTabsIntent = builder.build()
						customTabsIntent.launchUrl(context!!, avatar)
					}
					Glide.with(this).load(avatar).into(image)
				}

				binding.userNickname.text = user.username
				binding.userDiscriminator.text = getString(R.string.user_discriminator, user.discriminator)
				binding.userId.text = user.id
			}
			binding.swipeToRefresh.isRefreshing = false
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (checkNetworkConnection(context, root)) {
				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			profileViewModel.updateUserAsync()
		}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
