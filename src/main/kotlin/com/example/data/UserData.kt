package com.example.data

import com.example.data.model.PreKeyBundle
import com.example.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserData(
    private val db: CoroutineDatabase
) : UserDataSource{

    private val users = db.getCollection<User>()

    override suspend fun getAllUsers(): List<User> = users.find().descendingSort(User::id).toList()

    override suspend fun insertUser(user: User) {
        users.insertOne(user)
    }

    override suspend fun getUserById(id: String): User? = users.findOne(User::id eq id)

    override suspend fun getUserKeys(id: String): PreKeyBundle? = users.findOne(User::id eq id)?.preKeyBundle

}