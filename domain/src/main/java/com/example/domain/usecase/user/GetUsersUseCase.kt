package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getUsers(): Flowable<MutableList<User>> {
        return userRepository.getUsers()
    }
}
