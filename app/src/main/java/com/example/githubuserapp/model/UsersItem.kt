package com.example.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersItem(
    val login: String,
    val avatar_url: String
) : Parcelable