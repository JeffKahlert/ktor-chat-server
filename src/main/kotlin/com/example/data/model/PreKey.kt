package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
class PreKey(
    val id: String,
    val publicKey: String
) {
}