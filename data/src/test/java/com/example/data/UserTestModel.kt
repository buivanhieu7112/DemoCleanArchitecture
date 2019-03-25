package com.example.data

import com.example.data.model.UserEntity
import com.example.domain.model.User

fun createUserEntity(): UserEntity = UserEntity(
    "1",
    "hieu",
    "https://avatars0.githubusercontent.com/u/2?v=4"
)

fun createUser(): User = User(
    "2",
    "van",
    "https://avatars0.githubusercontent.com/u/1?v=4"
)
