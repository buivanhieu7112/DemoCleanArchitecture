package com.example.democleanarchitecture.model

import com.example.domain.model.User

fun createRepoItem(): RepoItem = RepoItem(
    id = "1",
    name = "hieu",
    avatar = "https://avatars2.githubusercontent.com/u/46143615?v=4"
)

fun createUser(): User = User(
    id = "2",
    name = "van",
    avatar = "https://avatars2.githubusercontent.com/u/46143615?v=4"
)
