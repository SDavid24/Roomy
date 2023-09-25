package com.example.roomy.dataobject

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var message: String = "",
    var senderId: String? = null,
    var senderRoom: String? = null,
    var messageKey: String? = null,
    var timeStamp: Long? = null,
    var dayMessageSent: String? = null,
    var timeMessageSent: String? = null,
    var dateMessageSent: String? = null
) : Serializable
