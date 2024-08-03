package com.example.plugins

import com.example.session.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSecurity() {
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }

    // Hier muss ich die Chatteilnehmer einfügen.
    // TODO: Geräte erkennen und einem User zuweisen, damit diese am Chat Teilnehmen
    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<ChatSession>() == null) {
            val userId = call.parameters["userId"] ?: "0"
            call.sessions.set(ChatSession("DUMMI", "DUMMI", "DUMMI"))
        }
    }
}
