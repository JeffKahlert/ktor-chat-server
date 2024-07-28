package com.example.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
class User(
    @BsonId
    val id: String = ObjectId().toString(),
    val name: String,
    val preKeyBundle: PreKeyBundle
)
{
}