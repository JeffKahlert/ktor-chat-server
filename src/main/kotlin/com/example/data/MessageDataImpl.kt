package com.example.data

import com.example.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MessageDataImpl(
    private val db: CoroutineDatabase
) : MessageDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages.find().descendingSort(Message::timestamp).toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }

    override suspend fun getMessageByUserId(userId: String): Message? {
        return messages.findOne(Message::senderId eq userId)
    }

    override suspend fun getMessagesByChatId(chatId: String): List<Message> {
        return messages.find(Message::chatId eq chatId.toCharArray().sorted().joinToString("")).toList()
    }
}