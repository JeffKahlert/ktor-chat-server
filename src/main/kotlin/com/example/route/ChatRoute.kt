package com.example.route

import com.example.room.ChatController
import com.example.session.ChatSession
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatRoute(chatController: ChatController) {
    webSocket("chat") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close()
            return@webSocket
        }
         try{
             chatController.join(
                 userId = session.participantOne,
                 sessionId = session.sessionId,
                 socket = this
             )
             incoming.consumeEach { frame ->
                 if (frame is Frame.Text) {
                     chatController.sendMessage(
                         senderId = session.participantOne,
                         receiverId = session.participantTwo,
                         messageContent = frame.readText()
                     )
                 }
             }
         } catch (ex: Exception) {
             ex.printStackTrace()
         } finally {
             chatController.leave(session.participantOne)
         }

        fun Route.getAllMessages(chatController: ChatController) {
            get("/messages") {
                call.respond(
                    HttpStatusCode.OK,
                    chatController.getAllMessages()
                )
            }
        }
    }
}