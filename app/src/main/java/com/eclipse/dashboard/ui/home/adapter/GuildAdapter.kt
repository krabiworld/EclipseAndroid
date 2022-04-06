package com.eclipse.dashboard.ui.home.adapter

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.databinding.GuildCardBinding
import com.eclipse.dashboard.ui.server.ServerActivity
import com.eclipse.dashboard.util.getGuildIcon

class GuildAdapter(private val fragment: Fragment, layout: Int, private var states: ArrayList<Guild>)
	: ArrayAdapter<Guild>(fragment.requireContext(), layout, states) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val inflater = fragment.layoutInflater

		val binding = if (convertView == null) {
			GuildCardBinding.inflate(inflater, parent, false)
		} else {
			GuildCardBinding.bind(convertView)
		}

		val state = states[position]

		binding.guildName.text = state.name

		// if icon (server avatar) is not null - set avatar and on click listener
        if (state.icon != null) {
			// get icon url with size 48
			val icon = getGuildIcon(state.id, state.icon)
			// load avatar by url
			Glide.with(fragment).load(icon).circleCrop().into(binding.guildAvatar)
		}

		val intent = Intent(context, ServerActivity::class.java)
		intent.putExtra("guildId", state.id)
		// server name for showing in title
		intent.putExtra("guildName", state.name)

		binding.guildManage.setOnClickListener {
			context.startActivity(intent)
		}

		return binding.root
    }
}
