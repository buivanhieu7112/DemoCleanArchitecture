package com.example.democleanarchitecture.domain.usecase

import com.example.democleanarchitecture.domain.createUser
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.user.SaveUserUseCase
import io.reactivex.Completable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SaveUserUseCaseTest {
    private lateinit var saveUserUseCase: SaveUserUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setUp() {
        saveUserUseCase = SaveUserUseCase(userRepository)
    }

    @After
    fun clear() {
        saveUserUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        val user = createUser()
        val params = SaveUserUseCase.Params(user)
        saveUserUseCase.createObservable(SaveUserUseCase.Params(params.user))
        verify(userRepository).insertUser(params.user)
    }

    @Test
    fun saveUserComplete() {
        val user = createUser()
        val params = SaveUserUseCase.Params(user)
        given(userRepository.insertUser(params.user)).willReturn(Completable.complete())

        val test = saveUserUseCase.createObservable(SaveUserUseCase.Params(user = user)).test()
        test.assertComplete()
    }
}
