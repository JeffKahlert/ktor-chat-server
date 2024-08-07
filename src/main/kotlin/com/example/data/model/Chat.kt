package com.example.data.model

import kotlinx.serialization.Serializable


@Serializable
class Chat(
    val chatId: String,
    val userId: String,
    val messages: List<Message>,
) {
}