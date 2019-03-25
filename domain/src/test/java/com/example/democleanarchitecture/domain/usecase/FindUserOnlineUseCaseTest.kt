package com.example.democleanarchitecture.domain.usecase

import com.example.democleanarchitecture.domain.createUser
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.user.FindUserOnlineUseCase
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class FindUserOnlineUseCaseTest {
    private lateinit var findUserOnlineUseCase: FindUserOnlineUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setUp() {
        findUserOnlineUseCase = FindUserOnlineUseCase(userRepository)
    }

    @After
    fun clear() {
        findUserOnlineUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        val params = FindUserOnlineUseCase.Params(name = anyString())
        findUserOnlineUseCase.createObservable(params)

        verify(userRepository).getUserBySearch(params.name)
    }

    @Test
    fun createObservableNull() {
        val test = findUserOnlineUseCase.createObservable(null).test()
        test.assertError { true }
    }

    @Test
    fun searchComplete() {
        given(userRepository.getUserBySearch(anyString())).willReturn(Flowable.just(mutableListOf(createUser())))
        val test = findUserOnlineUseCase.createObservable(FindUserOnlineUseCase.Params(anyString())).test()
        test.onComplete()
    }

    @Test
    fun searchReturnData() {
        val params = FindUserOnlineUseCase.Params(name = anyString())
        val user = createUser()
        `when`(userRepository.getUserBySearch(name = params.name)).thenReturn(Flowable.just(mutableListOf(user)))
        val test = findUserOnlineUseCase.createObservable(params).test()
        test.assertValue(mutableListOf(user))
    }
}
