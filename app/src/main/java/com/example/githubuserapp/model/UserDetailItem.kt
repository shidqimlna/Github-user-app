package com.example.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetailItem(
    val name: String? = null,
    val login: String? = null,
    val avatar_url: String? = null,
    val company: String? = null,
    val blog: String? = null,
    val location: String? = null,
    val email: String? = null,
    val bio: String? = null,
    val followers: String? = null,
    val following: String? = null
) : Parcelable