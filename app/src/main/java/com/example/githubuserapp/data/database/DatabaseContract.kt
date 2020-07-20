package com.example.githubuserapp.data.database

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by dicoding on 10/12/2017.
 */

object DatabaseContract {

    // Authority yang digunakan
    const val AUTHORITY = "com.example.githubuserapp"
    const val SCHEME = "content"

    /*
    Penggunaan Base Columns akan memudahkan dalam penggunaan suatu table
    Untuk id yang autoincrement sudah default ada di dalam kelas BaseColumns dengan nama field _ID
     */
    class NoteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "users_favorite"
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

            // Base content yang digunakan untuk akses content provider
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}
