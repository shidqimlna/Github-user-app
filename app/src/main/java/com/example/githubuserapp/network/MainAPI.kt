package com.example.githubuserapp.network

import com.example.githubuserapp.utils.ConstantValue.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainAPI {
    private var retrofit: Retrofit? = null
    val mainRepository: MainRepository
        get() {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit?.create(MainRepository::class.java)!!
        }
}