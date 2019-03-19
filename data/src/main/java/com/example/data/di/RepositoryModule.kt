package com.example.data.di

import com.example.data.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository
    {
        return userRepository
    }
}
