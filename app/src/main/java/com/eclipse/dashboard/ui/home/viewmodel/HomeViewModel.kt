package com.eclipse.dashboard.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eclipse.dashboard.data.model.User
import com.eclipse.dashboard.data.remote.discordAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {
	init {
	    updateUserAsync()
	}

	var tokenIsCorrupted = false

	private val user = MutableLiveData<User?>()

	fun getUser(): LiveData<User?> {
		return user
	}

	fun updateUserAsync() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val response = discordAPI.fetchUser().execute()
				if (response.isSuccessful) {
					user.postValue(response.body()!!)
				} else {
					if (response.code() == 401) tokenIsCorrupted = true
					throw IOException()
				}
			} catch (_: IOException) {
				user.postValue(null)
			}
		}
	}
}
