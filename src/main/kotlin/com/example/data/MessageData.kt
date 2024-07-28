package com.example.data

import com.example.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MessageData(
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
        return messages.findOne(Message::id eq userId)
    }
}