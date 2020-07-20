package com.example.githubuserapp.data.helper

import android.database.Cursor
import com.example.githubuserapp.data.database.DatabaseContract
import com.example.githubuserapp.model.UserItem
import java.util.*

object MappingHelper {

    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<UserItem> {
        val usersList = ArrayList<UserItem>()

        usersCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns.ID))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOGIN))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.AVATAR))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.NAME))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.COMPANY))
                val blog = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.BLOG))
                val location =
                    getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.LOCATION))
                val email = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.EMAIL))
                val bio = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.BIO))
                val follower =
                    getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOLLOWERS))
                val following =
                    getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOLLOWING))
                usersList.add(
                    UserItem(
                        id,
                        login,
                        avatar,
                        name,
                        company,
                        blog,
                        location,
                        email,
                        bio,
                        follower,
                        following
                    )
                )
            }
        }
        return usersList
    }
/*
    fun mapCursorToObject(notesCursor: Cursor?): UserItem {
        var userItem = UserItem()
        notesCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
            val title = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE))
            val description = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESCRIPTION))
            val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
            userItem = UserItem(id, title, description, date)
        }
        return userItem
    }*/
}
