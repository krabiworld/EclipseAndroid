package com.eclipse.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.local.PreferencesHelper
import com.eclipse.dashboard.databinding.FragmentDashboardBinding
import com.eclipse.dashboard.ui.home.adapter.GuildAdapter
import com.eclipse.dashboard.ui.home.model.DashboardViewModel
import com.eclipse.dashboard.util.checkNetworkConnection

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
	private val dashboardViewModel: DashboardViewModel by viewModels()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
		val root = binding.root

		if (dashboardViewModel.token.isEmpty()) {
			dashboardViewModel.token = PreferencesHelper.getEncrypted(context).getString("token", "")!!
		}
		if (dashboardViewModel.getGuilds().value == null) {
			binding.swipeToRefresh.isRefreshing = true
		}

		val guildsAdapter = GuildAdapter(context, this, R.layout.guild_card, ArrayList())
		binding.guildsList.adapter = guildsAdapter

		dashboardViewModel.getGuilds().observe(this) { guilds ->
			if (guilds != null) {
				guildsAdapter.clear()
				guildsAdapter.addAll(guilds)
				guildsAdapter.notifyDataSetChanged()
			}
			binding.swipeToRefresh.isRefreshing = false
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (checkNetworkConnection(context, root)) {
				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			dashboardViewModel.updateGuildsAsync()
		}

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
		_binding = null
    }
}
