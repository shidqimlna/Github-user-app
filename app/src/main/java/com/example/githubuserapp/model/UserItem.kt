package com.example.githubuserapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users_favorite")
data class UserItem(
    @PrimaryKey
    @ColumnInfo(index = true, name = "id") var id: Int? = null,
    @ColumnInfo(name = "login") var login: String? = null,
    @ColumnInfo(name = "avatar_url") val avatar_url: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "company") val company: String? = null,
    @ColumnInfo(name = "blog") val blog: String? = null,
    @ColumnInfo(name = "location") val location: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "bio") val bio: String? = null,
    @ColumnInfo(name = "followers") val followers: String? = null,
    @ColumnInfo(name = "following") val following: String? = null
) : Parcelable

