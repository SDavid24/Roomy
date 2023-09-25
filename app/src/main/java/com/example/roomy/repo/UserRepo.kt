package com.example.roomy.repo

import androidx.lifecycle.LiveData
import com.example.roomy.dao.UserDao
import com.example.roomy.dataobject.User

class UserRepo(private val userDao: UserDao) {

    val  getCurrentUser: LiveData<User>? = userDao.getCurrentUser()

    suspend fun addUser(user:User){
        userDao.saveNewUser(user)
    }

    suspend fun getUser() : User? {
        return userDao.getUser()
    }


}