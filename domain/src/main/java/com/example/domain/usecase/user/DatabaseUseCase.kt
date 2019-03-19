package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class DatabaseUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun insertUser(user: User): Completable {
        return userRepository.insertUser(user)
    }

    fun getUsersLocal(): Flowable<MutableList<User>> {
        return userRepository.getUsersLocal()
    }

    fun getUserLocalBySearch(name: String): Flowable<MutableList<User>> {
        return userRepository.getUserLocalBySearch(name)
    }

    fun deleteUser() {
        userRepository.deleteUser()
    }
}
