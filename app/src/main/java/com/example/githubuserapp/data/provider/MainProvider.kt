package com.example.githubuserapp.data.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuserapp.data.database.MainDatabase
import com.example.githubuserapp.data.database.UserFavoriteDAO
import com.example.githubuserapp.model.fromContentValues

class MainProvider : ContentProvider() {

    private val uriMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val authority = "com.example.githubuserapp"
    private val userId = 1
    private lateinit var userDao: UserFavoriteDAO

    override fun onCreate(): Boolean {
        userDao = MainDatabase.initDB(context!!).userDAO()
        uriMatcher.addURI(authority, "users_favorite", userId)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? =
        context?.let {
            when (userId) {
                uriMatcher.match(uri) -> {
                    val id = userDao.insert(
                        fromContentValues(
                            values!!
                        )
                    )
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

    /*override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        if (uriMatcher.match(uri) == userId) {
            val columns = arrayOf(
                "id",
                "login",
                "avatar_url",
                "name",
                "company",
                "blog",
                "location",
                "email",
                "bio",
                "followers",
                "following"
            )
            val matrixCursor = MatrixCursor(columns)
            val users: List<UserItem>? = ArrayList()

            for (userItem in users!!) {
                matrixCursor.addRow(
                    arrayOf(
                        userItem.id,
                        userItem.login,
                        userItem.avatar_url,
                        userItem.name,
                        userItem.company,
                        userItem.blog,
                        userItem.location,
                        userItem.email,
                        userItem.bio,
                        userItem.followers,
                        userItem.following
                    )
                )
            }

            return matrixCursor
        }

        return null
    }*/

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

    /*override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =
        if (context != null) {
            when (userId) {
                uriMatcher.match(uri) ->  {1
                    val count = userDao
                        // ContentUris.parseId(): Convert the last path segment to a long; -1 if the path is empty
                        .delete(ContentUris.parseId(uri))
                    context!!.contentResolver.notifyChange(uri, null)
                    count
                }
                else -> throw IllegalArgumentException("Unknown Uri: $uri")
            }
        } else {
            0
        }*/


    override fun getType(uri: Uri): String? {
        return null
    }
}
/*
    companion object {
        private const val GITHUB_USERS = 1
        private const val USERNAME_GITHUB_USER = 2
        private lateinit var userDao: UserFavoriteDAO
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, GITHUB_USERS)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USERNAME_GITHUB_USER)
        }
    }

    override fun onCreate(): Boolean {
        userDao = AppDatabase.initDB(context!!).userDAO()
        return false;
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor : Cursor
        return when (uriMatcher.match(uri)) {
            GITHUB_USERS ->  {
                cursor = userDao.getAllCursor()
                if (context != null) {
                    cursor.setNotificationUri(
                        context!!.contentResolver, uri
                    )
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            USERNAME_GITHUB_USER -> {
                cursor = userDao.getId(uri.lastPathSegment.toString())
                if (context != null) {
                    cursor.setNotificationUri(
                        context!!.contentResolver, uri
                    )
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (GITHUB_USERS) {
            uriMatcher.match(uri) -> {
                if (context != null) {
                    val id: Long = userDao.insertLong(contentValues(values!!)!!)
                    if (id != 0L) {
                        context!!.contentResolver
                            .notifyChange(GITHUB_USERS_URI, null)
                        return Uri.parse("$GITHUB_USERS_URI/${values.get("id")}")
                    }
                }
                throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            }
            else -> 0
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (USERNAME_GITHUB_USER) {
            uriMatcher.match(uri) -> if (context != null) {
                val count: Int = userDao.deleteId(ContentUris.parseId(uri))
                context!!.contentResolver.notifyChange(GITHUB_USERS_URI, null)
            }
            else -> 0
        }
    }

    override fun getType(uri: Uri): String? = null
}*/
