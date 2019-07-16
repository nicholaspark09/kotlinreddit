package com.example.networking

import android.annotation.SuppressLint
import android.content.Context

class Networking private constructor(
    val context: Context
) {

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