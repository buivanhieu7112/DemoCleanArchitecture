package com.example.data.repository

import com.example.data.UserRepositoryImpl
import com.example.data.local.db.AppDatabase
import com.example.data.model.UserEntityMapper
import com.example.data.remote.api.ApiService
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class UserRepositoryImplTest {
    private lateinit var userRepositoryImpl: UserRepositoryImpl

    private val userApiMock = mock(ApiService::class.java)
    private val appDatabaseMock = mock(AppDatabase::class.java)
    private val userEntityMapper = UserEntityMapper()

    @Before
    fun setup(){
        userRepositoryImpl = UserRepositoryImpl(userApiMock,appDatabaseMock,userEntityMapper)
    }

    @Test
    fun getUsersSuccess(){
        `when`(userApiMock.getUsers()).thenReturn(Flowable.error(Throwable("Null")))
    }
}
