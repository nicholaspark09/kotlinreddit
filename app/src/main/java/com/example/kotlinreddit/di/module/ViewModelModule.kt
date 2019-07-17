package com.example.kotlinreddit.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinreddit.ui.home.HomeViewModel
import com.example.kotlinreddit.viewmodel.RedditViewModelFactory
import com.example.kotlinreddit.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: RedditViewModelFactory): ViewModelProvider.Factory
}