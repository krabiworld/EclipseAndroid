package com.eclipse.dashboard.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.eclipse.dashboard.R
import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.ui.server.ServerActivity
import com.eclipse.dashboard.util.getAvatarUri
import com.eclipse.dashboard.util.launchUri

class GuildAdapter(
	context: Context?,
	private val fragment: Fragment,
	private var layout: Int,
	private var states: ArrayList<Guild>
	) : ArrayAdapter<Guild>(context!!, layout, states) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		var view: View? = convertView

        val inflater = LayoutInflater.from(context)
        if (view == null) view = inflater.inflate(layout, parent, false)

		val state: Guild = states[position]

		view!!.findViewById<TextView>(R.id.guild_name).text = state.name

        if (state.icon != null) {
			val avatar: ImageView = view.findViewById(R.id.guild_avatar)
			val avatarUri = getAvatarUri("icons", state.id, state.icon, 40)
			avatar.setOnClickListener {
				launchUri(context, getAvatarUri("icons", state.id, state.icon))
			}
			Glide.with(fragment).load(avatarUri).circleCrop().into(avatar)
		}

		view.findViewById<Button>(R.id.guild_manage).setOnClickListener {
			val intent = Intent(context, ServerActivity::class.java)

			intent.putExtra("guildId", state.id)
			intent.putExtra("guildName", state.name)

			context.startActivity(intent)
		}

        return view
    }
}
