package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.UserRepositoryImpl
import com.example.data.local.db.AppDatabase
import com.example.data.local.db.AppDatabase.Companion.DATABASE_NAME
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository {
        return userRepository
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}
