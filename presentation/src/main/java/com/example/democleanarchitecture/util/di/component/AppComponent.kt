package com.example.democleanarchitecture.util.di.component

import android.app.Application
import com.example.democleanarchitecture.MainApplication
import com.example.democleanarchitecture.util.di.modules.ActivityBuildersModule
import com.example.democleanarchitecture.util.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuildersModule::class, AppModule::class])
interface AppComponent : AndroidInjector<MainApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
