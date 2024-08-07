package com.example.data.model

import com.example.room.Participant
import kotlinx.serialization.Serializable


@Serializable
class Chat(
    val id: String,
    val userId: String,
    val messages: List<Message>,
) {
}