package com.example.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
class Message(
    @BsonId
    val id: String = ObjectId().toString(),
    val chatId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
) {
}