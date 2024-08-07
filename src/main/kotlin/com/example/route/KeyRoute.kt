package com.example.route

import com.example.data.KeyDataSource
import com.example.data.UserDataSource
import com.example.data.model.PreKeyBundle
import com.example.data.model.User
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.keys(keyDataSource: KeyDataSource, userDataSource: UserDataSource) {
    post("/keys") {
        try {
            val clientBundle = call.receive<PreKeyBundle>()

            val user = User(
                userId = clientBundle.deviceId,
                userName = clientBundle.userName,
                preKeyBundle = clientBundle
            )

            userDataSource.insertUser(user)
            keyDataSource.insertPreKeyBundle(clientBundle)

            call.respond(HttpStatusCode.OK)
        } catch (ex: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest)
        } catch (ex: JsonConvertException) {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
    get("/keys/{userId}") {
        val userId = call.parameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val preKeyBundle = userDataSource.getUserKeys(userId) ?: return@get call.respond(HttpStatusCode.NotFound)
        call.respond(preKeyBundle)
    }
}