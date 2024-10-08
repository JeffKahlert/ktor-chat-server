package com.example.plugins

import com.example.chat.ChatController
import com.example.data.KeyDataSource
import com.example.data.MessageDataSource
import com.example.data.UserDataSource
import com.example.data.model.Chat
import com.example.route.chat
import com.example.route.keys
import com.example.route.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userDataSource by inject<UserDataSource>()
    val keyDataSource by inject<KeyDataSource>()
    val messageDataSource by inject<MessageDataSource>()

    routing{
        keys(keyDataSource = keyDataSource, userDataSource = userDataSource)
        userRoute(userDataSource = userDataSource)
        chat(chatController = ChatController(messageDataSource))
    }
}
