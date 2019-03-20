package com.example.data

import com.example.data.local.db.AppDatabase
import com.example.data.model.UserEntityMapper
import com.example.data.remote.api.ApiService
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase,
    private val mapper: UserEntityMapper
) : UserRepository {
    override fun getUsers(): Flowable<MutableList<User>> {
        return apiService.getUsers().map { response ->
            response.map { mapper.mapToDomain(it) }.toMutableList()
        }.doOnError { Throwable("Not found!") }
    }

    override fun getUserBySearch(name: String): Flowable<MutableList<User>> {
        return apiService.getUserBySearch(name).map { response ->
            response.items.map { mapper.mapToDomain(it) }.toMutableList()
        }.doOnError { Throwable("Not found!") }
    }

    override fun insertUser(user: User): Completable {
        return appDatabase.userDao().insertUser(mapper.mapToEntity(user))
    }

    override fun getUsersLocal(): Flowable<MutableList<User>> {
        return appDatabase.userDao().getUsers()
            .map { response -> response.map { mapper.mapToDomain(it) }.toMutableList() }
            .doOnError { Throwable("Not found!") }
    }

    override fun getUserLocalBySearch(name: String): Flowable<MutableList<User>> {
        return appDatabase.userDao().getUserBySearch(name).map { response ->
            response.map { mapper.mapToDomain(it) }.toMutableList()
        }
    }

    override fun deleteUser(): Completable {
        return appDatabase.userDao().deleteAllUser()
    }
}
