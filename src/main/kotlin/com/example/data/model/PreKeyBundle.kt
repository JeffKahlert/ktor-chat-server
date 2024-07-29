package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
class PreKeyBundle(
    val userName: String,
    val registrationId: String,
    val identityKey: String,
    val preKeys: List<String>,
    val signedPreKeys: List<String>
) {

}