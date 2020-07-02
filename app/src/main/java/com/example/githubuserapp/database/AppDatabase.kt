package com.example.githubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.model.UserItem


@Database(entities = [UserItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserFavoriteDAO

    companion object {
        private var appDatabase: AppDatabase? = null
        fun initDB(context: Context): AppDatabase {
            if (appDatabase == null) appDatabase =
                Room.databaseBuilder(context, AppDatabase::class.java, "users_favorite")
                    .allowMainThreadQueries().build()
            return appDatabase as AppDatabase
        }
    }
}