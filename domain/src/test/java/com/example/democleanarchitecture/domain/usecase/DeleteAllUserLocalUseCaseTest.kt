package com.example.democleanarchitecture.domain.usecase

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.user.DeleteAllUserLocalUseCase
import io.reactivex.Completable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DeleteAllUserLocalUseCaseTest {
    private lateinit var deleteAllUserLocalUseCase: DeleteAllUserLocalUseCase

    private val userRepository = mock(UserRepository::class.java)

    @Before
    fun setUp() {
        deleteAllUserLocalUseCase = DeleteAllUserLocalUseCase(userRepository)
    }

    @After
    fun clear() {
        deleteAllUserLocalUseCase.onCleared()
    }

    @Test
    fun createObservable() {
        deleteAllUserLocalUseCase.createObservable()
        verify(userRepository).deleteUser()
    }

    @Test
    fun deleteAllUserComplete() {
        given(userRepository.deleteUser()).willReturn(Completable.complete())
        val test = deleteAllUserLocalUseCase.createObservable().test()
        test.assertComplete()
    }
}
