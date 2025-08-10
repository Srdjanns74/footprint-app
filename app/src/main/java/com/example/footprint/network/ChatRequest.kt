package com.example.footprint.network

data class ChatRequest(
    val model: String = "openai/gpt-3.5-turbo",
    val messages: List<Message>,
    val temperature: Double = 0.7
)
