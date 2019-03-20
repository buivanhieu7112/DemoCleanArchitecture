package com.example.domain.usecase

abstract class UseCase<in Params, out T> where T : Any {
    abstract fun createObservable(params: Params? = null): T

    abstract fun onCleared()
}
