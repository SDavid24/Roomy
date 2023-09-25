package com.example.roomy.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomy.dataobject.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewUser(user: User?)

    @Query("SELECT * FROM user_data")
    fun getCurrentUser(): LiveData<User>?

    @Query("SELECT * FROM user_data")
    suspend fun getUser(): User?

}