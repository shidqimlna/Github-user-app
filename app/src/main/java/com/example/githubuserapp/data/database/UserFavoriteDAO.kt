package com.example.githubuserapp.data.database

import android.database.Cursor
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

    @Query("SELECT * FROM users_favorite")
    fun getAllCursor(): Cursor

    /*@Delete
    fun deleteById(id: Long) : Int*/

    /*@Insert
    fun insertLong(userFavorites: UserItem) */

    @Insert
    fun insert(userFavorites: UserItem): Long

    @Delete
    fun delete(userFavorite: UserItem)

}
