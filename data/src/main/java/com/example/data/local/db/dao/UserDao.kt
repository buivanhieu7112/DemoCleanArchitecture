package com.example.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: UserEntity): Completable

    @Query("SELECT * FROM USER")
    fun getUsers(): Flowable<MutableList<UserEntity>>

    @Query("SELECT * FROM USER WHERE name LIKE :name")
    fun getUserBySearch(name: String): Flowable<MutableList<UserEntity>>

    @Query("DELETE FROM USER")
    fun deleteAllUser(): Completable
}
