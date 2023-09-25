package com.example.roomy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomy.database.RoomDB
import com.example.roomy.dataobject.User
import com.example.roomy.repo.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    val getCurrentUser : LiveData<User>?
    private val repository: UserRepo

    init{
        val userDao = RoomDB.getInstance(application).userDao()
        repository = UserRepo(userDao)
        getCurrentUser = repository.getCurrentUser
    }

    fun saveNewUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {

            repository.addUser(user)
        }
    }
    suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            repository.getUser()
        }
    }

}