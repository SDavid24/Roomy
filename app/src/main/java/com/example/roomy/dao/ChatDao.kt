package com.example.roomy.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomy.dataobject.Chat
import com.example.roomy.dataobject.User

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveMessage(chat: Chat?)

    /*@Query("SELECT * FROM user_data")
    fun getCurrentUser(): LiveData<User>?*/

    @Query("SELECT * FROM chat where senderRoom = :senderRoom ORDER BY timeStamp ASC ")
    fun getMessages(senderRoom: String): LiveData<List<Chat>?>

}