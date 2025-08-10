package com.example.footprint.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenRouterApi {
    val service: ChatService by lazy {
        Retrofit.Builder()
            .baseUrl("https://openrouter.ai/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatService::class.java)
    }
}
