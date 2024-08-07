package com.example.session

import io.ktor.websocket.*

data class ChatSession(
    val chatId: String,
    val userId: String
)