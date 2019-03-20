package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Flowable
import javax.inject.Inject

class ShowListUserLocalUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<ShowListUserLocalUseCase.InputEmpty, Flowable<MutableList<User>>>() {
    override fun createObservable(params: InputEmpty?): Flowable<MutableList<User>> {
        return userRepository.getUsersLocal()
    }

    override fun onCleared() {
    }

    class InputEmpty
}
