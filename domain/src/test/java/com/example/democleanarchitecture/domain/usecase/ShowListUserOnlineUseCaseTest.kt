package com.example.democleanarchitecture.domain.usecase

import com.example.democleanarchitecture.domain.createUser
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.user.ShowListUserOnlineUseCase
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ShowListUserOnlineUseCaseTest {

    private lateinit var showListUserOnlineUseCase: ShowListUserOnlineUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setUp() {
        showListUserOnlineUseCase = ShowListUserOnlineUseCase(userRepository)
    }

    @After
    fun clear() {
        showListUserOnlineUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        showListUserOnlineUseCase.createObservable()
        verify(userRepository).getUsers()
    }

    @Test
    fun getUserComplete() {
        given(userRepository.getUsers()).willReturn(Flowable.just(mutableListOf(createUser())))
        val test = showListUserOnlineUseCase.createObservable().test()
        test.assertComplete()
    }

    @Test
    fun getUserReturnData() {
        val users = mutableListOf(createUser())

        given(userRepository.getUsers()).willReturn(Flowable.just(users))
        val test = showListUserOnlineUseCase.createObservable().test()

        test.assertValue(users)
    }
}
