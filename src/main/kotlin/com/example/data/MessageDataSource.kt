package com.example.data

import com.example.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)

    suspend fun getMessageByUserId(userId: String): Message?

    suspend fun getMessagesByChatId(chatId: String): List<Message>?
}