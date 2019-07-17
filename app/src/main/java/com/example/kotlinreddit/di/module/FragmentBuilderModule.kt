package com.example.kotlinreddit.di.module

import com.example.kotlinreddit.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}