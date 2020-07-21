package com.example.favoriteapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.githubuserapp"
    const val SCHEME = "content"

    class NoteColumns : BaseColumns {

        companion object {
            private const val TABLE_NAME = "users_favorite"
            const val ID = "ID"
            const val LOGIN = "login"
            const val AVATAR = "avatar_url"
            const val NAME = "name"
            const val COMPANY = "company"
            const val BLOG = "blog"
            const val LOCATION = "location"
            const val EMAIL = "email"
            const val BIO = "bio"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}
