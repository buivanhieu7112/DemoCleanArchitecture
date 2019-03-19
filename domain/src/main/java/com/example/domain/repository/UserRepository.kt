package com.example.domain.repository

import com.example.domain.model.User
import io.reactivex.Completable
import io.reactivex.Flowable

interface UserRepository {
    fun getUsers(): Flowable<MutableList<User>>

    fun getUserBySearch(name: String): Flowable<MutableList<User>>

    fun insertUser(user: User): Completable

    fun getUsersLocal(): Flowable<MutableList<User>>

    fun getUserLocalBySearch(name: String): Flowable<MutableList<User>>

    fun deleteUser()
}
