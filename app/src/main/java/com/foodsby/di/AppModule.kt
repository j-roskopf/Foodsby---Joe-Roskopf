package com.foodsby.di

import android.app.Application
import android.content.Context
import com.foodsby.FoodsbyApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideApplication(application: FoodsbyApplication): Application {
        return application
    }

    @Provides
    internal fun provideContext(application: FoodsbyApplication): Context {
        return application.applicationContext
    }
}