package com.eclipse.bot.data.model

import android.os.Parcel
import android.os.Parcelable

data class GuildModel(val id: String, val name: String, val avatar: String) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(p0: Parcel?, p1: Int) {
        return
    }
}
