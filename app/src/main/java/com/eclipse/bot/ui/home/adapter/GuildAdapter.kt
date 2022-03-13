package com.eclipse.bot.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.eclipse.bot.R
import com.eclipse.bot.ui.ServerMangerActivity
import com.eclipse.bot.data.model.GuildModel
import com.eclipse.bot.util.CircleTransform
import com.squareup.picasso.Picasso

class GuildAdapter(context: Context?, private var layout: Int, private var states: ArrayList<GuildModel>)
	: ArrayAdapter<GuildModel>(context!!, layout, states) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView

        val inflater = LayoutInflater.from(context)
        if (view == null) view = inflater.inflate(layout, parent, false)

        val name: TextView = view!!.findViewById(R.id.guild_name)
        val image: ImageView = view.findViewById(R.id.guild_avatar)

        val state: GuildModel = states[position]

        name.text = state.name
        if (state.avatar.isEmpty()) {
			image.setImageResource(R.drawable.ic_discord_24dp)
        } else {
            Picasso.get().load(state.avatar).transform(CircleTransform()).into(image)
        }

        view.findViewById<Button>(R.id.manageGuild).setOnClickListener {
            val intent = Intent(context, ServerMangerActivity::class.java)

            intent.putExtra("guildId", state.id)
            intent.putExtra("guildName", state.name)

            context.startActivity(intent)
        }

        return view
    }
}
