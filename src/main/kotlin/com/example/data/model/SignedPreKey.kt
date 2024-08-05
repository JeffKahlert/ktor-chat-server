package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
class SignedPreKey(
    val id: String,
    val publicKey: String,
    val signature: String
) {
}