package com.example.roomy.utils

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson


class ListTypeConverter {
    @TypeConverter
    fun fromString(value: String): MutableList<String> {
        val listType = object : TypeToken<MutableList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: MutableList<String>): String {
        return Gson().toJson(list)
    }
}