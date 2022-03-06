package com.eclipse.bot.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.data.model.GuildModel
import com.eclipse.bot.databinding.FragmentDashboardBinding
import com.eclipse.bot.ui.main.adapter.GuildAdapter
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

    private var guilds: ArrayList<GuildModel>? = null
    private val guildsKey: String = "guilds"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val dialog = DialogUtil.networkUnavailableDialog(context)

        if (!NetworkUtil.isNetworkAvailable(context!!)) {
            dialog.show()
        }

        val token: String = PreferencesHelper.getSharedPreferences(context!!).getString("token", "")!!

        if (guilds == null) {
            guilds = if (savedInstanceState?.getParcelableArrayList<GuildModel>(guildsKey) == null) {
                runBlocking { getGuildsAsync(token); }
            } else {
                savedInstanceState.getParcelableArrayList(guildsKey)
            }
        }

        val guildsList: ListView = binding.guildsList
        val guildsAdapter = GuildAdapter(context, R.layout.guild_card, guilds!!)
        guildsList.adapter = guildsAdapter

        binding.swipeToRefresh.setOnRefreshListener {
            if (!NetworkUtil.isNetworkAvailable(context!!)) {
                dialog.show()

                binding.swipeToRefresh.isRefreshing = false
                return@setOnRefreshListener
            }

            guilds = runBlocking { getGuildsAsync(token) }

            guildsAdapter.clear()
            guildsAdapter.addAll(guilds!!)
            guildsAdapter.notifyDataSetChanged()

            binding.swipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(guildsKey, guilds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cancel()
    }

    private suspend fun getGuildsAsync(token: String): ArrayList<GuildModel> {
        val asyncJob = withContext(Dispatchers.IO) {
            val discordGuilds: List<Guild>

            try {
                discordGuilds = DiscordAPI(token).fetchGuilds()
            } catch (e: IOException) {
                return@withContext ArrayList()
            }

            val guildModels: ArrayList<GuildModel> = ArrayList()

            for (guild: Guild in discordGuilds) {
                if (guild.permissionList.contains(Permission.ADMINISTRATOR) || guild.isOwner) {
                    val avatar = "https://cdn.discordapp.com/icons/${guild.id}/${guild.icon}.png?size=40"
                    guildModels.add(GuildModel(guild.id, guild.name, if (guild.icon == null) "" else avatar))
                }
            }

            guildModels
        }

        return asyncJob
    }
}