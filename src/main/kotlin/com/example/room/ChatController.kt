package com.example.room

import com.example.data.MessageDataSource
import com.example.data.UserDataSource
import com.example.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatController(
    private val messageDataSource: MessageDataSource,
    private val userDataSource: UserDataSource
) {
    private val participants = ConcurrentHashMap<String, Participant>()

    suspend fun join(userId: String, sessionId: String, socket: WebSocketSession) {
        val user = userDataSource.getUserById(userId)
        if (user != null) {
            val participant = Participant(user, sessionId, socket)
            participants[userId] = participant
        } else {
            socket.close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "User not found"))
        }
    }

    suspend fun sendMessage(senderId: String, receiverId: String, messageContent: String) {

        val message = Message(
            messageContent,
            senderId,
            receiverId,
            System.currentTimeMillis(),
        )
        messageDataSource.insertMessage(message)

        val receiver = participants[receiverId]
        receiver?.socket?.send(Frame.Text(Json.encodeToString(message)))
    }

    private fun findReceiverId(receiverId: String): String {
        return participants.keys.first { it != receiverId }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun leave(userId: String){
        participants[userId]?.socket?.close();
        participants.remove(userId)
    }
}