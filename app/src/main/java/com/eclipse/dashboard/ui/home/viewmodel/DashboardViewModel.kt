package com.eclipse.dashboard.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eclipse.dashboard.data.local.Token
import com.eclipse.dashboard.data.model.Guild
import com.eclipse.dashboard.data.remote.discordAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class DashboardViewModel : ViewModel() {
	private val guilds: MutableLiveData<ArrayList<Guild>?> by lazy {
		MutableLiveData<ArrayList<Guild>?>().also {
			updateGuildsAsync()
		}
	}

	fun getGuilds(): LiveData<ArrayList<Guild>?> {
		return guilds
	}

	fun updateGuildsAsync() {
		viewModelScope.launch(Dispatchers.IO) {
			val discordGuilds: ArrayList<Guild>

			try {
				val response = discordAPI.fetchGuilds("Bearer $Token").execute()
				if (response.isSuccessful) {
					discordGuilds = response.body()!!
				} else {
					throw IOException()
				}
			} catch (_: IOException) {
				guilds.postValue(null)
				return@launch
			}

			val filteredGuilds: ArrayList<Guild> = ArrayList()
			val administrator: Long = 0x8
			for (guild: Guild in discordGuilds) {
				if (guild.permissions and administrator == administrator || guild.owner) {
					filteredGuilds.add(guild)
				}
			}

			guilds.postValue(filteredGuilds)
		}
	}
}
