package com.example.roomy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomy.database.RoomDB
import com.example.roomy.dataobject.Chat
import com.example.roomy.repo.ChatRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _myChats = MutableLiveData<List<Chat>?>() // Corrected type

    val myChatList: MutableLiveData<List<Chat>?> = _myChats

    //val getCurrentUser : LiveData<User>?
    private val repository: ChatRepo

    init{
        val chatDao = RoomDB.getInstance(application).chatDao()
        repository = ChatRepo(chatDao)
        //getCurrentUser = repository.getCurrentUser
    }

    fun saveNewMessage(chat: Chat) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveMessage(chat)

            // After saving, update _myDataList with the new list of messages
            val currentChatList = _myChats.value ?: emptyList()
            _myChats.postValue(currentChatList + chat)
        }
    }

    fun getMessages(senderRoom: String) {
        val messagesLiveData = repository.getMessages(senderRoom)

        // Observe the LiveData and update _myChats when data is received
        messagesLiveData.observeForever { messages ->
            _myChats.value = messages
        }
    }


    /*fun saveNewMessage(chat: Chat){
        viewModelScope.launch(Dispatchers.IO) {

            repository.saveMessage(chat)
        }
    }


    fun getMessages(senderRoom: String): LiveData<List<Chat>?> {
        return repository.getMessages(senderRoom)
    }
*/
  /*  suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            repository.getUser()
        }
    }*/


}