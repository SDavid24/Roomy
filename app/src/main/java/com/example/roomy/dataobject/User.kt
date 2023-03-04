package com.example.roomy.dataobject

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var mobile: Long = 0,
    var birthday: String = "",
    var interests: MutableList<String> = ArrayList(),
    val displayImage: String = "",
    val coverImage: String = "",
    val fcmToken: String = "",
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readLong(),
        source.readString()!!,
        source.createStringArrayList()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(firstName)
        writeString(lastName)
        writeString(email)
        writeLong(mobile)
        writeString(birthday)
        writeStringList(interests)
        writeString(displayImage)
        writeString(coverImage)
        writeString(fcmToken)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }


}