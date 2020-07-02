package com.example.githubuserapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuserapp.model.UserItem

@Dao
interface UserFavoriteDAO {
    @Query("SELECT * FROM users_favorite")
    fun getAll(): List<UserItem>

    @Query("SELECT EXISTS (SELECT 1 FROM users_favorite WHERE id = :id)")
    fun exists(id: Int): Boolean

    @Insert
    fun insert(vararg userFavorites: UserItem)

    @Delete
    fun delete(userFavorite: UserItem)

}