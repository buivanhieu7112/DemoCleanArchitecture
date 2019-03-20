package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Flowable
import javax.inject.Inject

open class ShowListUserOnlineUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<ShowListUserOnlineUseCase.InputEmpty, Flowable<MutableList<User>>>() {
    override fun createObservable(params: InputEmpty?): Flowable<MutableList<User>> {
        return userRepository.getUsers()
    }

    override fun onCleared() {
    }

    class InputEmpty
}
