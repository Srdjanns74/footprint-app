package com.example.footprint.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatService {
    @Headers(
        "Authorization: Bearer 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",
        "Content-Type: application/json"
    )
    @POST("chat/completions")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}
