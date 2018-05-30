package com.foodsby.di

import com.foodsby.FoodsbyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    BuildersModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(foodsbyApplication: FoodsbyApplication): AppComponent.Builder
        fun build(): AppComponent
    }

    fun inject(foodsbyApplication: FoodsbyApplication)
}
