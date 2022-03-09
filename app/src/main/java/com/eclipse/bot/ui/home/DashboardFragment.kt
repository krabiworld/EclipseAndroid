package com.eclipse.bot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.data.model.GuildModel
import com.eclipse.bot.databinding.FragmentDashboardBinding
import com.eclipse.bot.ui.home.adapter.GuildAdapter
import com.eclipse.bot.ui.home.model.DashboardViewModel
import com.eclipse.bot.util.DialogUtil
import com.eclipse.bot.util.NetworkUtil
import io.mokulu.discord.oauth.DiscordAPI
import io.mokulu.discord.oauth.model.Guild
import io.mokulu.discord.oauth.model.Permission
import kotlinx.coroutines.*
import java.io.IOException

class DashboardFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

	private val model: DashboardViewModel by viewModels()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

		val dialog = DialogUtil.networkUnavailableDialog(context)
		if (!NetworkUtil.isNetworkAvailable(context)) {
			dialog.show()
		}

		val token: String = PreferencesHelper.getSharedPreferences(context).getString("token", "")!!

		val guildsList: ListView = binding.guildsList
		val guildsAdapter = GuildAdapter(context, R.layout.guild_card, ArrayList())
		guildsList.adapter = guildsAdapter

		if (model.getGuilds()!!.isEmpty()) {
			setGuildListAsync(guildsAdapter, token)
		} else {
			guildsAdapter.addAll(model.getGuilds()!!)
			guildsAdapter.notifyDataSetChanged()
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (!NetworkUtil.isNetworkAvailable(context!!)) {
				dialog.show()

				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			setGuildListAsync(guildsAdapter, token)

			binding.swipeToRefresh.isRefreshing = false
		}

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
		_binding = null
    }

	private fun setGuildListAsync(guildsAdapter: GuildAdapter, token: String) {
		launch(Dispatchers.IO) {
			val discordGuilds: List<Guild>

			try {
				discordGuilds = DiscordAPI(token).fetchGuilds()
			} catch (e: IOException) {
				launch(Dispatchers.Main) {
					Toast.makeText(context, R.string.stop_please, Toast.LENGTH_SHORT).show()
				}
				return@launch
			}

			val guildModels: ArrayList<GuildModel> = ArrayList()
			for (guild: Guild in discordGuilds) {
				if (guild.permissionList.contains(Permission.ADMINISTRATOR) || guild.isOwner) {
					val avatar = "https://cdn.discordapp.com/icons/${guild.id}/${guild.icon}.png?size=40"
					guildModels.add(GuildModel(guild.id, guild.name, if (guild.icon == null) "" else avatar))
				}
			}

			launch(Dispatchers.Main) {
				model.setGuilds(guildModels)
				guildsAdapter.clear()
				guildsAdapter.addAll(guildModels)
				guildsAdapter.notifyDataSetChanged()
			}
		}
	}
}
