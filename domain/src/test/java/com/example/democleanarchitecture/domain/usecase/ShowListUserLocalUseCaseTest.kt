package com.example.democleanarchitecture.domain.usecase

import com.example.democleanarchitecture.domain.createUser
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.user.ShowListUserLocalUseCase
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class ShowListUserLocalUseCaseTest {
    private lateinit var showListUserLocalUseCase: ShowListUserLocalUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setUp() {
        showListUserLocalUseCase = ShowListUserLocalUseCase(userRepository)
    }

    @After
    fun clear(){
        showListUserLocalUseCase.onCleared()
    }

    @Test
    fun createObservable(){
        showListUserLocalUseCase.createObservable()
        verify(userRepository).getUsersLocal()
    }

    @Test
    fun getUsersLocalComplete(){
        given(userRepository.getUsersLocal()).willReturn(Flowable.just(mutableListOf(createUser())))
        val test = showListUserLocalUseCase.createObservable().test()
        test.onComplete()
    }

    @Test
    fun getUsersLocalReturnData(){
        val users = mutableListOf(createUser())
        `when`(userRepository.getUsersLocal()).thenReturn(Flowable.just(users))

        val test = showListUserLocalUseCase.createObservable().test()
        test.assertValue(users)

    }
}