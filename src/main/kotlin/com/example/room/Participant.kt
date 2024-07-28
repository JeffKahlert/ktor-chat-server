package com.example.room

import com.example.data.model.User
import io.ktor.websocket.*


class Participant(
    val user: User,
    val sessionId: String,
    val socket: WebSocketSession,
) {
}