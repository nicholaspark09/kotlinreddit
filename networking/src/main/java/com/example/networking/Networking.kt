package com.example.networking

import android.annotation.SuppressLint
import android.content.Context
import com.example.networking.di.NetworkModule
import com.example.networking.di.PersistenceModule
import com.example.networking.repo.RedditDataSource
import com.example.networking.repo.RedditRepository

class Networking private constructor(
    val context: Context,
    val networkModule: NetworkModule = NetworkModule.get(context)
) {

    val persistenceModule: PersistenceModule by lazy { PersistenceModule.get(context) }
    val redditRepository: RedditDataSource by lazy {
        RedditRepository(
            networkModule.redditApi,
            persistenceModule.redditPersistence
        )
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: Networking? = null

        @JvmStatic
        @Synchronized
        fun init(context: Context) {
            if (INSTANCE == null) {
                synchronized(Networking::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Networking(context)
                    }
                }
            }
        }

        @JvmStatic
        fun get(): Networking {
            if (INSTANCE == null) {
                throw IllegalStateException("Please call Networking.init(context) before using the library")
            }
            return INSTANCE!!
        }
    }
}