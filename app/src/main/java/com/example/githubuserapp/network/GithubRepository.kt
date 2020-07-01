package com.example.githubuserapp.network

import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.model.SearchUsersResponse
import com.example.githubuserapp.model.UserDetailItem
import com.example.githubuserapp.model.UsersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubRepository {
    @GET("users?")
    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    fun getUsersAll(
        @Query("since") random: Int
    ): Call<ArrayList<UsersItem>>

    @GET("search/users?")
    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    fun getUsersSearch(
        @Query("q") searchKey: String
    ): Call<SearchUsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<UsersItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: Token ${BuildConfig.API_KEY}")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<UsersItem>>

}