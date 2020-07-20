package com.example.githubuserapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.model.UserItem

@Database(entities = [UserItem::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun userDAO(): UserFavoriteDAO

    companion object {
        private var mainDatabase: MainDatabase? = null
        fun initDB(context: Context): MainDatabase {
            if (mainDatabase == null) mainDatabase =
                Room.databaseBuilder(context, MainDatabase::class.java, "users_favorite")
                    .allowMainThreadQueries().build()
            return mainDatabase as MainDatabase
        }
    }
}