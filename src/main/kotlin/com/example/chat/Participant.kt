package com.example.chat

import io.ktor.websocket.*

data class Participant(
    val userId: String,
    val sessionId: String,
    val socket: WebSocketSession,
) {

}