package com.example.kotlinreddit.di

import android.app.Application
import com.example.kotlinreddit.KotlinReddit
import com.example.kotlinreddit.di.module.AppModule
import com.example.kotlinreddit.di.module.MainActivityModule
import com.example.networking.repo.RedditDataSource
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class
    ]
)
interface ApplicationComponent {

    fun inject(kotlinReddit: KotlinReddit)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun redditRepository(redditRepo: RedditDataSource): Builder
    }
}