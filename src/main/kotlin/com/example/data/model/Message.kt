package com.example.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
class Message(
    val content: String,
    val senderId: String,
    val receiverId: String,
    val timestamp: Long,
    @BsonId
    val id: String = ObjectId().toString()
) {
}