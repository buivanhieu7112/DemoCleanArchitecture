package com.example.data

import com.example.data.model.UserEntityMapper
import com.example.data.remote.api.ApiService
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: UserEntityMapper
) : UserRepository {
    override fun getUsers(): Flowable<MutableList<User>> {
        return apiService.getUsers().map { response ->
            response.map { mapper.mapToDomain(it) }.toMutableList()
        }.doOnError {
            Throwable("Not found!")
        }
    }

    override fun getUserBySearch(name: String): Flowable<MutableList<User>> {
        return apiService.getUserBySearch(name).map { response ->
            response.items.map { mapper.mapToDomain(it) }.toMutableList()
        }.doOnError {
            Throwable("Not found!")
        }
    }
}
