package com.example.data

import com.example.data.model.Message
import com.example.session.ChatSession
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import io.ktor.websocket.*
import java.util.concurrent.ConcurrentHashMap


class ChatDataImpl(
    private val db: CoroutineDatabase
) : ChatDataSource {

    private val sessions = mutableListOf<ChatSession>()

    override suspend fun insertMessage(message: Message) {
        val collection = db.getCollection<Message>()
        collection.insertOne(message)
    }

    override suspend fun getMessages(chatId: String): List<Message> {
        val collection = db.getCollection<Message>()
        return collection.find(Message::chatId eq chatId.toCharArray().sorted().joinToString("")).toList()
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

    override suspend fun broadcastMessage(message: Message, members: ConcurrentHashMap<String, WebSocketSession>) {
        members.forEach { (_, session) ->
            session.send(Frame.Text("From ${message.senderId}: ${message.content}"))
        }
    }
}