package com.eclipse.bot.ui.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eclipse.bot.data.model.UserModel

class ProfileViewModel : ViewModel() {
	private val user: MutableLiveData<UserModel?> = MutableLiveData(null)

	fun getUser(): UserModel? {
		return user.value
	}

	fun setUser(user: UserModel) {
		this.user.value = user
	}
}
