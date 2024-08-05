package com.example.route

import com.example.data.UserDataSource
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(
    userDataSource: UserDataSource
) {
    get("/user") {
        try {
            call.respond(
                HttpStatusCode.OK,
                userDataSource.getAllUsers()
            )
        } catch (ex: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest)
        } catch (ex: JsonConvertException) {
            call.respond(HttpStatusCode.BadRequest)
        } catch (ex: Exception) {
        call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${ex.message}")
    }
    }
}