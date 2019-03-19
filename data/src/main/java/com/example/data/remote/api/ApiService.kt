package com.example.data.remote.api

import com.example.data.model.UserEntity
import com.example.data.response.SearchRepoResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(): Flowable<MutableList<UserEntity>>

    @GET("search/users")
    fun getUserBySearch(@Query("q") name: String): Flowable<SearchRepoResponse>
}
