package com.example.githubuserapp.utils

import android.content.ContentValues
import com.example.githubuserapp.model.UserItem

fun fromContentValues(contentValues: ContentValues?): UserItem {
    val userItem = UserItem()
    contentValues?.apply {
        if (containsKey("id")) {
            userItem.id = getAsInteger("id")
        }
        if (containsKey("login")) {
            userItem.login = getAsString("login")
        }
    }
    return userItem
}