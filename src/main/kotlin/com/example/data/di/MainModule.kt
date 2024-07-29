package com.example.data.di

import com.example.data.*
import org.koin.dsl.module
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.coroutine

val appModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase("ba_app_db")
    }
    /*single {
        ChatController(get(), get())
    }

    single<MessageDataSource> {
        MessageData(get())
    }*/

     single<UserDataSource> {
         UserDataImpl(get())
     }

    single<KeyDataSource> {
        KeyDataImpl(get())
    }
}