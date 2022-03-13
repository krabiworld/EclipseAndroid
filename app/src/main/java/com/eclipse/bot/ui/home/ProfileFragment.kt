package com.eclipse.bot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.data.model.UserModel
import com.eclipse.bot.databinding.FragmentProfileBinding
import com.eclipse.bot.ui.home.model.ProfileViewModel
import com.eclipse.bot.util.CircleTransform
import com.eclipse.bot.util.DialogUtil
import com.eclipse.bot.util.NetworkUtil
import com.squareup.picasso.Picasso
import io.mokulu.discord.oauth.DiscordAPI
import io.mokulu.discord.oauth.model.User
import kotlinx.coroutines.*
import java.io.IOException

class ProfileFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
	private val model: ProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dialog = DialogUtil.networkUnavailableDialog(context)
        if (!NetworkUtil.isNetworkAvailable(context!!)) {
            dialog.show()
        }

        val token: String = PreferencesHelper.getEncrypted(context!!).getString("token", "")!!
		if (model.getUser() == null) {
			updateUserAsync(token)
		} else {
			updateUserUIAsync(model.getUser()!!, false)
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (!NetworkUtil.isNetworkAvailable(context!!)) {
				dialog.show()

				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			updateUserAsync(token)

			binding.swipeToRefresh.isRefreshing = false
		}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cancel() // Watch him! This small pidor maybe deliver us problems...
    }

    private fun updateUserAsync(token: String) {
        launch(Dispatchers.IO) {
            val discordUser: User
            try {
                discordUser = DiscordAPI(token).fetchUser()
            } catch (e: IOException) {
				launch(Dispatchers.Main) {
					Toast.makeText(context, R.string.stop_please, Toast.LENGTH_SHORT).show()
				}
				return@launch
            }

            val avatar: String = if (discordUser.avatar.isEmpty()) {
                ""
            } else {
                "https://cdn.discordapp.com/avatars/${discordUser.id}/${discordUser.avatar}.png?size=240"
            }

			updateUserUIAsync(UserModel(discordUser.id, discordUser.fullUsername, avatar), true)
        }
    }

	private fun updateUserUIAsync(user: UserModel, update: Boolean) {
		launch(Dispatchers.Main) {
			if (update) {
				model.setUser(user)
			}

			val image: ImageView = binding.userAvatar

			if (user.avatar.isEmpty()) {
				image.setImageResource(R.drawable.ic_discord_24dp)
			} else {
				Picasso.get().load(user.avatar).transform(CircleTransform()).into(image)
			}

			binding.userNickname.text = user.nickname
			binding.userId.text = user.id
		}
	}
}
