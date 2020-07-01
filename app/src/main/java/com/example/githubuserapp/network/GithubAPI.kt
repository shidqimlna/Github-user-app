package com.example.githubuserapp.network

import com.example.githubuserapp.ConstantValue.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubAPI {
    private var retrofit: Retrofit? = null
    val githubRepository: GithubRepository
        get() {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit?.create(GithubRepository::class.java)!!
        }
}