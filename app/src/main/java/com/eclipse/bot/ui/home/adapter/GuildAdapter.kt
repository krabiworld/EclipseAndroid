package com.eclipse.bot.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.eclipse.bot.R
import com.eclipse.bot.data.model.Guild
import com.eclipse.bot.ui.ServerManagerActivity
import com.eclipse.bot.util.CircleTransform
import com.squareup.picasso.Picasso

class GuildAdapter(context: Context?, private var layout: Int, private var states: ArrayList<Guild>)
	: ArrayAdapter<Guild>(context!!, layout, states) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView

        val inflater = LayoutInflater.from(context)
        if (view == null) view = inflater.inflate(layout, parent, false)

        val name: TextView = view!!.findViewById(R.id.guild_name)
        val image: ImageView = view.findViewById(R.id.guild_avatar)

        val state: Guild = states[position]

        name.text = state.name
        if (state.icon == null) {
			image.setImageResource(R.drawable.ic_discord_24dp)
        } else {
			val avatar = "https://cdn.discordapp.com/icons/${state.id}/${state.icon}.png?size=40"
            Picasso.get().load(avatar).transform(CircleTransform()).into(image)
        }

		view.findViewById<Button>(R.id.guild_manage).setOnClickListener {
			val intent = Intent(context, ServerManagerActivity::class.java)

			intent.putExtra("guildId", state.id)
			intent.putExtra("guildName", state.name)

			context.startActivity(intent)
		}

        return view
    }
}
