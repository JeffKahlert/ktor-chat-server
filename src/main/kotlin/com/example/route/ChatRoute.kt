package com.example.route

import com.example.data.ChatDataSource
import com.example.data.model.Message
import com.example.session.ChatSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.util.concurrent.ConcurrentHashMap

fun Route.chat(chatDataSource: ChatDataSource) {
    val members = ConcurrentHashMap<String, WebSocketSession>()

    webSocket("/chat/{chatId}/{userId}") {
        val chatId = call.parameters["chatId"] ?: return@webSocket close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "No chatId"))
        val userId = call.parameters["userId"] ?: return@webSocket close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "No userId"))

        val chatSession = ChatSession(chatId, userId)
        call.sessions.set(chatSession)

        members[userId] = this

        try {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val receivedText = frame.readText()
                    val message = Message(
                        chatId = chatId,
                        senderId = userId,
                        receiverId = chatId.split("-")[1],
                        content = receivedText
                    )
                    chatDataSource.insertMessage(message)
                    chatDataSource.broadcastMessage(message, members)
                }
            }
        } finally {
            members.remove(userId)
            chatDataSource.removeSession(chatId, userId)
        }
    }

    get("/messages/{chatId}") {
        val chatId = call.parameters["chatId"] ?: return@get
        call.respond(
            HttpStatusCode.OK,
            chatDataSource.getMessages(chatId)
        )
    }
}