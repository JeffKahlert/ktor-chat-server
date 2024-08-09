package com.example.route

import com.example.chat.ChatController
import com.example.data.ChatDataSource
import com.example.data.MessageDataSource
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

fun Route.chat(
    chatController: ChatController,
) {
    webSocket("/chat/{chatId}/{userId}") {
        val chatId = call.parameters["chatId"] ?: return@webSocket close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "No chatId"))
        val userId = call.parameters["userId"] ?: return@webSocket close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "No userId"))

        val chatSession = call.sessions.get<ChatSession>()
        if (chatSession == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No chatId"))
            return@webSocket
        }


        try {
            chatController.joinChat(userId, chatId, this)
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val receivedText = frame.readText()
                    chatController.sendMessage(
                        chatId,
                        receivedText
                    )
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            chatController.disconnect(chatSession.userId)
        }
    }

    get("/messages/{chatId}") {
        val chatId = call.parameters["chatId"] ?: return@get
        val messages = chatController.getAllMessagesFromChatId(chatId) ?: emptyList()
        call.respond(
            HttpStatusCode.OK,
            messages
        )
    }
}