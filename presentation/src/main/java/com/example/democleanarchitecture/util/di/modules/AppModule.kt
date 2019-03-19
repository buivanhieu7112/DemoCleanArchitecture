package com.example.democleanarchitecture.util.di.modules

import android.app.Application
import android.content.Context
import com.example.data.di.NetworkModule
import com.example.data.di.RepositoryModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkModule::class, RepositoryModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}
