package com.eclipse.dashboard.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.ui.server.ServerActivity
import com.eclipse.dashboard.util.getGuildIcon

class ServerAdapter(private val fragment: Fragment, private var states: ArrayList<Guild>)
	: RecyclerView.Adapter<ServerAdapter.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.server_list_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val context = fragment.requireContext()
		val state = states[position]

		holder.name.text = state.name
		holder.memberCount.text = context.getString(R.string.message_member_count, state.memberCount)
		// if icon (server avatar) is not null - set avatar
        if (state.icon != null) {
			// get icon url with size 512
			val icon = getGuildIcon(state.id, state.icon, 512)
			// load avatar by url
			Glide.with(fragment).load(icon).into(holder.avatar)
		}

		val intent = Intent(context, ServerActivity::class.java)
		intent.putExtra("guildId", state.id)
		// server name for showing in title
		intent.putExtra("guildName", state.name)

		holder.card.setOnClickListener {
			context.startActivity(intent)
		}
	}

	override fun getItemCount() = states.size

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val card: CardView = view.findViewById(R.id.server_card)
		val avatar: ImageView = view.findViewById(R.id.guild_avatar)
		val name: TextView = view.findViewById(R.id.guild_name)
		val memberCount: TextView = view.findViewById(R.id.guild_member_count)
	}
}
