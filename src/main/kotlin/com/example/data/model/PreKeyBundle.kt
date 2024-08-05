package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
class PreKeyBundle(
    val userName: String,
    val deviceId: String,
    val registrationId: String,
    val identityKey: String,
    val preKeys: List<PreKey>,
    val signedPreKeys: List<SignedPreKey>
) {

}