package com.example.roomy.dataobject

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.roomy.utils.ListTypeConverter

@Entity(tableName = "user_data")
@TypeConverters(ListTypeConverter::class) // Apply the TypeConverter here
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String = "",
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
        source.readInt()!!,
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
        writeInt(id)
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