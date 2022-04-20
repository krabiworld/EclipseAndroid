package com.eclipse.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.databinding.FragmentServersBinding
import com.eclipse.dashboard.ui.home.adapter.ServerAdapter
import com.eclipse.dashboard.ui.home.viewmodel.ServersViewModel
import com.eclipse.dashboard.util.isNetworkUnavailable
import com.google.android.material.snackbar.Snackbar

class ServersFragment : Fragment() {
	private val guilds: ArrayList<Guild> = ArrayList()
    private var _binding: FragmentServersBinding? = null
    private val binding get() = _binding!!
	private val serversViewModel: ServersViewModel by viewModels()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentServersBinding.inflate(inflater, container, false)
		val root = binding.root

		binding.swipeToRefresh.apply {
			setColorSchemeResources(android.R.color.white)
			setProgressBackgroundColorSchemeResource(R.color.eclipse_primary)
		}

		if (serversViewModel.getGuilds().value == null) {
			binding.swipeToRefresh.isRefreshing = true
		}

		val guildsAdapter = ServerAdapter(this, guilds)
		binding.serverList.apply {
			layoutManager = GridLayoutManager(requireContext(), 2)
			adapter = guildsAdapter
			itemAnimator = null
		}

		serversViewModel.getGuilds().observe(viewLifecycleOwner) { newGuilds ->
			if (newGuilds != null) {
				if (newGuilds.isEmpty()) {
					changeVisibility(true)
				}

				guilds.clear()
				guilds.addAll(newGuilds)
				guildsAdapter.notifyItemRangeChanged(0, newGuilds.size)

				changeVisibility(false)
			}

			binding.swipeToRefresh.isRefreshing = false
		}

		binding.swipeToRefresh.setOnRefreshListener {
			if (isNetworkUnavailable(requireContext())) {
				Snackbar.make(root, R.string.message_no_connection, Snackbar.LENGTH_SHORT).show()
				binding.swipeToRefresh.isRefreshing = false
				return@setOnRefreshListener
			}

			serversViewModel.updateGuildsAsync()
		}

        return root
    }

	override fun onResume() {
		super.onResume()
		if (!isNetworkUnavailable(requireContext())) {
			serversViewModel.updateGuildsAsync()
		}
	}

    override fun onDestroyView() {
        super.onDestroyView()
		_binding = null
    }

	// if isEmpty true - show message list empty and hide server list
	private fun changeVisibility(isEmpty: Boolean) {
		binding.messageListEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
		binding.serverList.visibility = if (isEmpty) View.GONE else View.VISIBLE
	}
}
