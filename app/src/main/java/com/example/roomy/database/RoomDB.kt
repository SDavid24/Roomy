package com.example.roomy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomy.dao.ChatDao
import com.example.roomy.dao.UserDao
import com.example.roomy.dataobject.Chat
import com.example.roomy.dataobject.User


@Database (entities = [
    User::class,
    Chat::class
        ]
    , version = 1, exportSchema = false )


abstract class RoomDB : RoomDatabase() {


    companion object{

        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB {

            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java,
                        "room_db"
                    ).build()

                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }



    /**firstly, connect the database to the Dao*/
    abstract fun userDao(): UserDao
    abstract fun chatDao(): ChatDao

}