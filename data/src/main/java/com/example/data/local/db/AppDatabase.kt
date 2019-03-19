package com.example.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.db.AppDatabase.Companion.DATABASE_VERSION
import com.example.data.local.db.dao.UserDao
import com.example.data.model.UserEntity

@Database(entities = [UserEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Room-database"
    }
}
