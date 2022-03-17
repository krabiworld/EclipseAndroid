package com.eclipse.bot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.databinding.FragmentProfileBinding
import com.eclipse.bot.ui.home.model.ProfileViewModel
import com.eclipse.bot.util.CircleTransform
import com.eclipse.bot.util.SnackbarUtil
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
	private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
		binding.swipeToRefresh.isRefreshing = true

		if (SnackbarUtil.checkNetworkConnection(context, activity)) {
			binding.swipeToRefresh.isRefreshing = false
		}

		if (profileViewModel.token.isEmpty()) {
			profileViewModel.token = PreferencesHelper.getEncrypted(context).getString("token", "")!!
		}

		profileViewModel.getUserLiveData().observe(this) { user ->
			if (user == null) {
				SnackbarUtil.tooManyRequests(activity)
				binding.swipeToRefresh.isRefreshing = false
				return@observe
			}

			val image: ImageView = binding.userAvatar
			if (user.avatar == null) {
				image.setImageResource(R.drawable.ic_discord_24dp)
			} else {
				val avatar = "https://cdn.discordapp.com/avatars/${user.id}/${user.avatar}.png?size=240"
				Picasso.get().load(avatar).transform(CircleTransform()).into(image)
			}

			binding.userNickname.text = user.username
			binding.userId.text = user.id

			binding.swipeToRefresh.isRefreshing = false
		}

		if (profileViewModel.getUserLiveData().value!!.id == "") {
			profileViewModel.updateUserAsync()
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (SnackbarUtil.checkNetworkConnection(context, activity)) {
				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			profileViewModel.updateUserAsync()
		}

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
