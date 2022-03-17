package com.eclipse.bot.ui.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eclipse.bot.data.model.Guild
import com.eclipse.bot.util.NetworkUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class DashboardViewModel : ViewModel(), CoroutineScope by MainScope() {
	private val guilds: MutableLiveData<ArrayList<Guild>?> = MutableLiveData(ArrayList())
	var token: String = ""

	fun getGuilds(): LiveData<ArrayList<Guild>?> {
		return guilds
	}

	fun updateGuildsAsync() {
		launch(Dispatchers.IO) {
			val guildsModels: ArrayList<Guild>

			try {
				val response = NetworkUtil.discordService.fetchGuilds("Bearer $token").execute()
				if (response.isSuccessful) {
					guildsModels = response.body()!!
				} else {
					guilds.postValue(null)
					throw IOException()
				}
			} catch (_: IOException) {
				return@launch
			}

			val filteredGuilds: ArrayList<Guild> = ArrayList()
			val administrator: Long = 0x8
			for (guild: Guild in guildsModels) {
				if (guild.permissions and administrator == administrator || guild.owner) {
					filteredGuilds.add(guild)
				}
			}

			guilds.postValue(filteredGuilds)
		}
	}
}
