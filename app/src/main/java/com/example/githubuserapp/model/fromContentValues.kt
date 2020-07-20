package com.example.githubuserapp.model

import android.content.ContentValues

fun fromContentValues(contentValues: ContentValues?): UserItem {
    val userItem = UserItem()
    contentValues?.apply {
        /**
         * Return true if ContentValue has the named value
         */
        if (containsKey("id")) {
            userItem.id = getAsInteger("id")
        }
        if (containsKey("login")) {
            userItem.login = getAsString("login")
        }
    }
    return userItem
}