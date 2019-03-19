package com.example.democleanarchitecture.model

import com.example.democleanarchitecture.base.ItemMapper
import com.example.democleanarchitecture.base.ModelItem
import com.example.domain.model.Model
import com.example.domain.model.User
import javax.inject.Inject

data class RepoItem(
    var id: String,
    var name: String,
    var avatar: String
) : ModelItem()

class RepoItemMapper @Inject constructor() : ItemMapper<User, RepoItem> {
    override fun mapToPresentation(model: User): RepoItem = RepoItem(
        id = model.id,
        name = model.name,
        avatar = model.avatar
    )

    override fun mapToDoMain(modelItem: RepoItem) = User(
        id = modelItem.id,
        name = modelItem.name,
        avatar = modelItem.avatar
    )
}
