package com.example.data

import com.example.data.model.PreKeyBundle
import com.example.data.model.User

interface UserDataSource {

    suspend fun getAllUsers(): List<User>
    suspend fun insertUser(user: User)
    suspend fun getUserById(id: String): User?
    suspend fun getUserKeys(id: String): PreKeyBundle?
}