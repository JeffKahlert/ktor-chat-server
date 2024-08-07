package com.example.data

import com.example.data.model.Chat
import com.example.data.model.Message
import com.example.session.ChatSession

interface ChatDataSource {

    suspend fun getAllChats(): List<Chat>

    suspend fun insertChat(chat: Chat)

    suspend fun saveMessage(message: Message)

    suspend fun getMessages(chatId: String): List<Message>

    suspend fun addSession(session: ChatSession)

    suspend fun removeSession(chatId: String, userId: String)

    suspend fun getSession(chatId: String, userId: String): ChatSession?

    suspend fun broadcastMessage(message: Message)
}