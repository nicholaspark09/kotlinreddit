package com.example.kotlinreddit

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import com.example.kotlinreddit.di.ApplicationInjector
import com.example.networking.Networking
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

const val TAG = "KotlinReddit"

class KotlinReddit : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        Networking.init(applicationContext)
        ApplicationInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}