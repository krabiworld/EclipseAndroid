package com.eclipse.bot.ui.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eclipse.bot.data.model.User
import com.eclipse.bot.util.NetworkUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class ProfileViewModel : ViewModel(), CoroutineScope by MainScope() {
	private val user: MutableLiveData<User?> = MutableLiveData(User("", "", "", ""))
	var token: String = ""

	fun getUserLiveData(): LiveData<User?> {
		return user
	}

	fun updateUserAsync() {
		launch(Dispatchers.IO) {
			val discordUser: User

			try {
				discordUser = NetworkUtil.discordService.fetchUser("Bearer $token").execute().body()!!
			} catch (_: IOException) {
				user.postValue(null)
				return@launch
			}

			user.postValue(discordUser)
		}
	}
}
