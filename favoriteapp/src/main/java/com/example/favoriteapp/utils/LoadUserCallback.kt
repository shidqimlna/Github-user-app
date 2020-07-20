package com.example.favoriteapp.utils

import android.database.Cursor

internal interface LoadUserCallback {
    fun postExecute(cursor: Cursor?)
}