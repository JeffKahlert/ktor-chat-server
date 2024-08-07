package com.example.data.model

import kotlinx.serialization.Serializable


@Serializable
class Chat(
    val id: String,
    val userId: String,
    val messages: List<Message>,
) {
}