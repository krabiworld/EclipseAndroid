package com.eclipse.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eclipse.dashboard.R
import com.eclipse.dashboard.databinding.FragmentDashboardBinding
import com.eclipse.dashboard.ui.home.adapter.GuildAdapter
import com.eclipse.dashboard.ui.home.viewmodel.DashboardViewModel
import com.eclipse.dashboard.util.isNetworkUnavailable
import com.google.android.material.snackbar.Snackbar

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
	private val dashboardViewModel: DashboardViewModel by viewModels()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
		val root = binding.root

		if (dashboardViewModel.getGuilds().value == null) {
			binding.swipeToRefreshText.isRefreshing = true
		}

		val guildsAdapter = GuildAdapter(this, R.layout.guild_card, ArrayList())
		binding.guildsList.adapter = guildsAdapter

		dashboardViewModel.getGuilds().observe(viewLifecycleOwner) { guilds ->
			if (guilds != null) {
				guildsAdapter.clear()
				guildsAdapter.addAll(guilds)
				guildsAdapter.notifyDataSetChanged()
				binding.swipeToRefreshText.visibility = View.GONE
				binding.swipeToRefreshList.visibility = View.VISIBLE
			}

			binding.swipeToRefreshText.isRefreshing = false
			binding.swipeToRefreshList.isRefreshing = false
		}

		val refreshes = listOf(binding.swipeToRefreshText, binding.swipeToRefreshList)

		refreshes.forEach { refresh ->
			refresh.setOnRefreshListener {
				updateGuilds(root)
			}
		}

        return root
    }

	override fun onResume() {
		super.onResume()
		if (!isNetworkUnavailable(requireContext())) {
			dashboardViewModel.updateGuildsAsync()
		}
	}

    override fun onDestroyView() {
        super.onDestroyView()
		_binding = null
    }

	private fun updateGuilds(root: CoordinatorLayout) {
		if (isNetworkUnavailable(requireContext())) {
			Snackbar.make(root, R.string.message_no_connection, Snackbar.LENGTH_SHORT).show()
			binding.swipeToRefreshText.isRefreshing = false
			binding.swipeToRefreshList.isRefreshing = false
			return
		}

		dashboardViewModel.updateGuildsAsync()
	}
}
