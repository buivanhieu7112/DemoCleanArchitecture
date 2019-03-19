package com.example.domain.repository

import com.example.domain.model.User
import io.reactivex.Flowable

interface UserRepository{
    fun getUsers(): Flowable<MutableList<User>>

    fun getUserBySearch(name: String): Flowable<MutableList<User>>
}
