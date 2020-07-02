package com.example.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchUsersResponse(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: ArrayList<UserItem>
) : Parcelable