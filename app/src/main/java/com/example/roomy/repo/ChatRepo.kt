package com.example.roomy.repo

import androidx.lifecycle.LiveData
import com.example.roomy.dao.ChatDao
import com.example.roomy.dao.UserDao
import com.example.roomy.dataobject.Chat
import com.example.roomy.dataobject.User

class ChatRepo(private val chatDao: ChatDao) {

    //val  getCurrentChatWithRecipient: LiveData<Chat>? = chatDao.getCurrentUser()

    suspend fun saveMessage(chat: Chat){
        chatDao.saveMessage(chat)
    }


    fun getMessages(senderRoom: String): LiveData<List<Chat>?> {
        return chatDao.getMessages(senderRoom)
    }

    /*  suspend fun getUser() : User? {
          return chatDao.getUser()
      }*/

}