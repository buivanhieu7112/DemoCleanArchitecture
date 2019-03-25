package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Flowable
import javax.inject.Inject

open class FindUserOnlineUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<FindUserOnlineUseCase.Params, Flowable<MutableList<User>>>() {
    override fun createObservable(params: Params?): Flowable<MutableList<User>> {
        params?.let { return userRepository.getUserBySearch(name = it.name) }
        return Flowable.error(Throwable("Params in not found"))
    }

    override fun onCleared() {
    }

    data class Params(val name: String)
}
