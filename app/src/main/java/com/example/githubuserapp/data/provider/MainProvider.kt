package com.example.githubuserapp.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.data.database.MainDatabase
import com.example.githubuserapp.data.database.UserFavoriteDAO
import com.example.githubuserapp.utils.fromContentValues

class MainProvider : ContentProvider() {

    private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val authority = "com.example.githubuserapp"
    private val userId = 1
    private lateinit var userDao: UserFavoriteDAO

    override fun onCreate(): Boolean {
        context?.let { userDao = MainDatabase.initDB(it).userDAO() }
        uriMatcher.addURI(authority, "users_favorite", userId)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? =
        context?.let {
            when (userId) {
                uriMatcher.match(uri) -> {
                    val id = userDao.insert(fromContentValues(values))
                    it.contentResolver
                        .notifyChange(uri, null)
                    return@let ContentUris.withAppendedId(uri, id)
                }
                else -> throw IllegalArgumentException("Unknown Uri: $uri")
            }
        }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            1 -> cursor = userDao.getAllCursor()
            else -> throw IllegalArgumentException("Unknown uri $uri")
        }
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}