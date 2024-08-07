package com.example.plugins

import com.example.session.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<ChatSession>("CHAT_SESSION")
    }

    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<ChatSession>() == null) {
            val chatId = call.parameters["chatId"] ?: "UNKNOWN_CHAT"
            val userId = call.parameters["userId"] ?: "UNKNOWN_USER"
            call.sessions.set(ChatSession(chatId, userId))
        }
    }
}
