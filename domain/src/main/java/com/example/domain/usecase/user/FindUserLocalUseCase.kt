package com.example.domain.usecase.user

import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UseCase
import io.reactivex.Flowable
import javax.inject.Inject

class FindUserLocalUseCase @Inject constructor(
    private val userRepository: UserRepository
) : UseCase<FindUserLocalUseCase.Params, Flowable<MutableList<User>>>() {
    override fun createObservable(params: Params?): Flowable<MutableList<User>> {
        params?.let { return userRepository.getUserLocalBySearch(name = it.name) }
        // toàn bộ block trong này sẽ không được chạy nếu `params` null và trong trường hợp `params` khác null
        // thì ta có thể sử dụng biến params thông qua "it", khi này "it" là một "params" (không chứa chấm hỏi)
        return Flowable.error(Throwable("Params input not valid"))
    }

    override fun onCleared() {
    }

    data class Params(val name: String)
}
