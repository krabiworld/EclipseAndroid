package com.eclipse.bot.ui.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eclipse.bot.data.model.GuildModel

class DashboardViewModel : ViewModel() {
	private val guilds: MutableLiveData<ArrayList<GuildModel>> = MutableLiveData(ArrayList())

	fun getGuilds(): ArrayList<GuildModel>? {
		return guilds.value
	}

	fun setGuilds(guildModels: ArrayList<GuildModel>) {
		guilds.value = guildModels
	}
}
