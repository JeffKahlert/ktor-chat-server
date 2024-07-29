package com.example.data

import com.example.data.model.PreKeyBundle
import org.litote.kmongo.coroutine.CoroutineDatabase

class KeyDataImpl(
    private val db: CoroutineDatabase
) : KeyDataSource {

    private val preKeyBundles = db.getCollection<PreKeyBundle>()

    override suspend fun insertPreKeyBundle(preKeyBundle: PreKeyBundle) {
        preKeyBundles.insertOne(preKeyBundle)
    }

}