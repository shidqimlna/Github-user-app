package com.example.githubuserapp.data.network

import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.model.SearchUsersResponse
import com.example.githubuserapp.model.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApiInterface {

    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    @GET("users?")
    fun getUsersAll(
        @Query("since") random: Int
    ): Call<ArrayList<UserItem>>

    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    @GET("search/users?")
    fun getUsersSearch(
        @Query("q") searchKey: String
    ): Call<SearchUsersResponse>

    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserItem>

    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<UserItem>>

    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UserItem>>

}