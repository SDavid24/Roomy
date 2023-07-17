package com.example.roomy.dataobject

import android.os.Parcel
import android.os.Parcelable

data class Apartment(
    var id: Int,
    var image: ArrayList<Int>,
    var description: String,
    var furnishStatus: String,
    var area: String,
    var state: String,
    var rentPrice: String
)/*: Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun describeContents() = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeStringList(image)
        writeString(description)
        writeString(furnishStatus)
        writeString(area)
        writeString(state)
        writeString(rentPrice)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Apartment> = object : Parcelable.Creator<Apartment> {
            override fun createFromParcel(source: Parcel): Apartment = Apartment(source)
            override fun newArray(size: Int): Array<Apartment?> = arrayOfNulls(size)
        }
    }
}*/
