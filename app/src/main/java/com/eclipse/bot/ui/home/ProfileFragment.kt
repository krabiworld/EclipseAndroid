package com.eclipse.bot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.data.model.UserModel
import com.eclipse.bot.databinding.FragmentProfileBinding
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

    private var user: UserModel? = null
    private val userKey: String = "user"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dialog = DialogUtil.networkUnavailableDialog(context)

        if (!NetworkUtil.isNetworkAvailable(context!!)) {
            dialog.show()
        }

        val token: String = PreferencesHelper.getSharedPreferences(context!!).getString("token", "")!!

        if (user == null) {
            user = if (savedInstanceState?.getParcelable<UserModel>(userKey) == null) {
                runBlocking { getUserAsync(token) }
            } else {
                savedInstanceState.getParcelable(userKey)
            }
        }

        val image: ImageView = binding.userAvatar

        if (user!!.avatar.isEmpty()) {
            image.setImageResource(R.drawable.ic_discord_white_24dp)
        } else {
            Picasso.get().load(user!!.avatar).transform(CircleTransform()).into(image)
        }

        binding.userNickname.text = user!!.nickname
        binding.userId.text = user!!.id

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(userKey, user)
    }

    private suspend fun getUserAsync(token: String): UserModel {
        val asyncJob = withContext(Dispatchers.IO) {
            val discordUser: User
            try {
                discordUser = DiscordAPI(token).fetchUser()
            } catch (e: IOException) {
                return@withContext UserModel("", "", "")
            }

            val avatar: String = if (discordUser.avatar.isEmpty()) {
                ""
            } else {
                "https://cdn.discordapp.com/avatars/${discordUser.id}/${discordUser.avatar}.png?size=240"
            }

            UserModel(discordUser.id, discordUser.fullUsername, avatar)
        }

        return asyncJob
    }
}
