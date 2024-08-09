package com.example.chat

import com.example.data.MessageDataSource
import com.example.data.model.Message
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatController(
    private val messageDataSource: MessageDataSource
) {

    private val participants = ConcurrentHashMap<String, Participant>()

    fun joinChat(
        userId: String,
        chatId: String,
        socket: WebSocketSession,
    ) {
        participants[userId] = Participant(
            userId,
            chatId,
            socket
        )
    }

    suspend fun sendMessage(chatId: String, messageContent: String) {
        val sortDigitChatId = chatId.toCharArray().sorted().joinToString("")
        participants.values.forEach { p ->
            val message = Message(
                chatId = sortDigitChatId,
                senderId = chatId.first().toString(),
                receiverId = chatId.last().toString(),
                content = messageContent
            )
            messageDataSource.insertMessage(message)

            val parsedMessages = Json.encodeToString(message)
            p.socket.send(Frame.Text(parsedMessages))
        }
    }

    suspend fun getAllMessagesFromChatId(chatId: String): List<Message>? {
        return messageDataSource.getMessagesByChatId(chatId)
    }

    suspend fun disconnect(userId: String) {
        participants[userId]?.socket?.close()
        if(participants.containsKey(userId)) {
            participants.remove(userId)
        }
    }
}