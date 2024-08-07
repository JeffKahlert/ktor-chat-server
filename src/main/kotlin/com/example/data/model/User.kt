package com.example.data.model

import io.ktor.websocket.*
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
class User(
    @BsonId
    val userId: String,
    val userName: String,
    val preKeyBundle: PreKeyBundle
)
{
}