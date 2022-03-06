package com.eclipse.bot.data.model

import android.os.Parcel
import android.os.Parcelable

class UserModel(id: String, nickname: String, avatar: String) : Parcelable {
    var id: String = ""
    var nickname: String = ""
    var avatar: String = ""

    init {
        this.id = id
        this.nickname = nickname
        this.avatar = avatar
    }

    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(p0: Parcel?, p1: Int) {
        return
    }
}