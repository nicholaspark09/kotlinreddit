package com.example.kotlinreddit.di.module

import androidx.lifecycle.ViewModelProvider
import com.example.kotlinreddit.viewmodel.RedditViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: RedditViewModelFactory): ViewModelProvider.Factory
}