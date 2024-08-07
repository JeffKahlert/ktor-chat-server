package com.example.data

import com.example.data.model.Chat
import com.example.data.model.Message
import com.example.session.ChatSession
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import io.ktor.websocket.*


class ChatDataImpl(
    private val db: CoroutineDatabase
) : ChatDataSource {

    private val chats = db.getCollection<Chat>()
    private val sessions = mutableListOf<ChatSession>()

    override suspend fun getAllChats(): List<Chat> {
        return chats.find().toList()
    }

    override suspend fun insertChat(chat: Chat) {
        chats.insertOne(chat)
    }
    override suspend fun saveMessage(message: Message) {
        val collection = db.getCollection<Message>()
        collection.insertOne(message)
    }

    override suspend fun getMessages(chatId: String): List<Message> {
        val collection = db.getCollection<Message>()
        return collection.find(Message::chatId eq chatId).toList()
    }

    override suspend fun addSession(session: ChatSession) {
        sessions.add(session)
    }

    override suspend fun removeSession(chatId: String, userId: String) {
        sessions.removeIf { it.chatId == chatId && it.userId == userId }
    }

    override suspend fun getSession(chatId: String, userId: String): ChatSession? {
        return sessions.find { it.chatId == chatId && it.userId == userId }
    }

    override suspend fun broadcastMessage(message: Message) {
        sessions.filter { it.chatId == message.chatId && it.userId == message.receiverId }.forEach { chatSession ->
            chatSession.session.send(Frame.Text(message.content))
        }
    }
}