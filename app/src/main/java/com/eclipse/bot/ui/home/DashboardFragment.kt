package com.eclipse.bot.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eclipse.bot.R
import com.eclipse.bot.data.local.PreferencesHelper
import com.eclipse.bot.databinding.FragmentDashboardBinding
import com.eclipse.bot.ui.home.adapter.GuildAdapter
import com.eclipse.bot.ui.home.model.DashboardViewModel
import com.eclipse.bot.util.SnackbarUtil

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
	private val dashboardViewModel: DashboardViewModel by viewModels()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

		binding.swipeToRefresh.isRefreshing = true

		if (SnackbarUtil.checkNetworkConnection(context, activity)) {
			binding.swipeToRefresh.isRefreshing = false
		}


		val guildsAdapter = GuildAdapter(context, R.layout.guild_card, ArrayList())
		binding.guildsList.adapter = guildsAdapter

		if (dashboardViewModel.token.isEmpty()) {
			dashboardViewModel.token = PreferencesHelper.getEncrypted(context).getString("token", "")!!
		}

		dashboardViewModel.getGuilds().observe(this) { guilds ->
			if (guilds == null) {
				SnackbarUtil.tooManyRequests(activity)
				binding.swipeToRefresh.isRefreshing = false
				return@observe
			}
			guildsAdapter.clear()
			guildsAdapter.addAll(guilds)
			guildsAdapter.notifyDataSetChanged()
			binding.swipeToRefresh.isRefreshing = false
		}

		if (dashboardViewModel.getGuilds().value!!.isEmpty()) {
			dashboardViewModel.updateGuildsAsync()
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (SnackbarUtil.checkNetworkConnection(context, activity)) {
				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			dashboardViewModel.updateGuildsAsync()
		}

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
		_binding = null
    }
}
