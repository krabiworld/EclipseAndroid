package com.eclipse.dashboard.ui.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eclipse.dashboard.data.remote.discordAPI
import com.eclipse.dashboard.util.getAvatarUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel : ViewModel() {
    var token: String = ""

    private val avatar: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            updateAvatarAsync()
        }
    }

    fun getAvatar(): LiveData<String> {
        return avatar
    }

    fun updateAvatarAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = discordAPI.fetchUser("Bearer $token").execute()
                if (response.isSuccessful) {
                    val user = response.body()!!

                    val avatarUri = if (user.avatar == null) ""
                    else getAvatarUri("avatars", user.id, user.avatar, 44).toString()

                    avatar.postValue(avatarUri)
                } else {
                    throw IOException()
                }
            } catch (_: IOException) {
                avatar.postValue("")
            }
        }
    }
}
