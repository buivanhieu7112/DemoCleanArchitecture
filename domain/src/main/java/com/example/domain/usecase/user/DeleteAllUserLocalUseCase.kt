package com.example.domain.usecase.user

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Completable
import javax.inject.Inject

open class DeleteAllUserLocalUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<DeleteAllUserLocalUseCase.InputEmpty, Completable>() {
    override fun createObservable(params: InputEmpty?): Completable {
        return userRepository.deleteUser()
    }

    override fun onCleared() {
    }

    class InputEmpty
}
