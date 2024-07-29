package com.example.data

import com.example.data.model.PreKeyBundle

interface KeyDataSource {

    suspend fun insertPreKeyBundle(preKeyBundle: PreKeyBundle)
}