package com.example.networking.di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.networking.repo.RedditPersistence
import com.example.networking.repo.RedditPersistenceContract
import com.google.gson.Gson

internal const val SHARED_KEY = "key:shared_networking"

class PersistenceModule(
    val context: Context
) {

    val sharedPreferences: SharedPreferences by lazy { context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE) }
    val gson: Gson by lazy { Gson() }
    val redditPersistence: RedditPersistenceContract by lazy { RedditPersistence(gson, sharedPreferences) }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: PersistenceModule? = null

        @JvmStatic
        fun get(context: Context): PersistenceModule {
            if (INSTANCE == null) {
                synchronized(PersistenceModule::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = PersistenceModule(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}