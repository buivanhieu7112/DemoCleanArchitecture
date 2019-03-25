package com.example.democleanarchitecture.domain.usecase

import com.example.democleanarchitecture.domain.createUser
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.user.FindUserLocalUseCase
import io.reactivex.Flowable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

class FindUserLocalUseCaseTest {
    private lateinit var findUserLocalUseCase: FindUserLocalUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setUp() {
        findUserLocalUseCase = FindUserLocalUseCase(userRepository)
    }

    @After
    fun clear() {
        findUserLocalUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        val params = FindUserLocalUseCase.Params(anyString())
        findUserLocalUseCase.createObservable(FindUserLocalUseCase.Params(params.name))
        verify(userRepository).getUserLocalBySearch(params.name)
    }

    @Test
    fun findUserLocalComplete() {
        given(userRepository.getUserLocalBySearch(anyString())).willReturn(Flowable.just(mutableListOf(createUser())))
        val test = findUserLocalUseCase.createObservable(FindUserLocalUseCase.Params(anyString())).test()
        test.assertComplete()
    }

    @Test
    fun findUserLocalReturnData() {
        val users = mutableListOf(createUser())
        val params = FindUserLocalUseCase.Params(name = anyString())

        `when`(userRepository.getUserLocalBySearch(params.name)).thenReturn(Flowable.just(users))
        val test = findUserLocalUseCase.createObservable(params).test()
        test.assertValue(users)
    }
}
