package com.example.kotlinreddit.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.example.kotlinreddit.KotlinReddit
import com.example.networking.Networking
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object ApplicationInjector {

    fun init(kotlinReddit: KotlinReddit) {
        DaggerApplicationComponent.builder()
            .application(kotlinReddit)
            .redditRepository(Networking.get().redditRepository)
            .build()
            .inject(kotlinReddit)
    }

    fun injectIfLifecycleIsSupported(kotlinReddit: KotlinReddit) {
        kotlinReddit.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {}

            override fun onActivityResumed(activity: Activity?) {}

            override fun onActivityStarted(activity: Activity?) {

            }

            override fun onActivityDestroyed(activity: Activity?) {
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.let { injectIntoActivity(it) }
            }

        })
    }

    private fun injectIntoActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            AndroidSupportInjection.inject(f)
                        }
                    }, true
                )
        }
    }
}