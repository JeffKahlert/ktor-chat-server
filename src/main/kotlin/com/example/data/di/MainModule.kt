package com.example.data.di

import com.example.data.MessageData
import com.example.data.MessageDataSource
import com.example.data.UserData
import com.example.data.UserDataSource
import com.example.room.ChatController
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine

val appModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("ba_app_db")
    }
    single {
        ChatController(get(), get())
    }

    single<MessageDataSource> {
        MessageData(get())
    }

     single<UserDataSource> {
         UserData(get())
     }
}