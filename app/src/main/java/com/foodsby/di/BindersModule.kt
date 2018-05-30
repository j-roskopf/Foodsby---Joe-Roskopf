package com.foodsby.di

import com.foodsby.main.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Binds all sub-components within the app.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity
}