package com.eclipse.dashboard.ui.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eclipse.dashboard.data.model.User
import com.eclipse.dashboard.data.remote.discordAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ProfileViewModel : ViewModel() {
	var token: String = ""

	private val user: MutableLiveData<User?> by lazy {
		MutableLiveData<User?>().also {
			updateUserAsync()
		}
	}

	fun getUser(): LiveData<User?> {
		return user
	}

	fun updateUserAsync() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val response = discordAPI.fetchUser("Bearer $token").execute()
				if (response.isSuccessful) {
					user.postValue(response.body()!!)
				} else {
					throw IOException()
				}
			} catch (_: IOException) {
				user.postValue(null)
			}
		}
	}
}
