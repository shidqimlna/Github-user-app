package com.example.githubuserapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubAPI {
    private var retrofit: Retrofit? = null
    val githubRepository: GithubRepository
        get() {
            val BASE_URL = "http://api.github.com/"
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit?.create(GithubRepository::class.java)!!
        }
}