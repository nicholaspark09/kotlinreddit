package com.example.networking.di

import android.annotation.SuppressLint
import android.content.Context
import com.example.networking.R
import com.example.networking.api.RedditApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule(
    val context: Context
) {
    val gson: Gson by lazy { Gson() }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .connectTimeout(context.resources.getInteger(R.integer.connect_timeout).toLong(), TimeUnit.SECONDS)
            .readTimeout(context.resources.getInteger(R.integer.read_timeout).toLong(), TimeUnit.SECONDS)
            .writeTimeout(context.resources.getInteger(R.integer.write_timeout).toLong(), TimeUnit.SECONDS)
            .build()
    }
    val endpoint: String by lazy { context.getString(R.string.networking_endpoint) }

    val isDebug: Boolean by lazy { context.resources.getBoolean(R.bool.networking_debug_enabled) }

    val interceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if(isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(endpoint)
            .build()
    }

    val redditApi: RedditApi by lazy {
        retrofit.create(RedditApi::class.java)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: NetworkModule? = null

        @JvmStatic
        fun get(context: Context): NetworkModule {
            if (INSTANCE == null) {
                synchronized(NetworkModule::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = NetworkModule(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}