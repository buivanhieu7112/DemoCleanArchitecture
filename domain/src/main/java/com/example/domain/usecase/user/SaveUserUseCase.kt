package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Completable
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<SaveUserUseCase.Params?, Completable>() {
    override fun createObservable(params: Params?): Completable {
        if (params != null) {
            return userRepository.insertUser(params.user)
        }
        return Completable.error(Throwable("Params input is null"))
    }

    override fun onCleared() {
        // if Y want subscribe in UseCase
        // Please unSubscribe it
    }

    data class Params(val user: User)
}
