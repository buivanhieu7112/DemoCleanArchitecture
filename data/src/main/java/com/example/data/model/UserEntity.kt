package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.base.EntityMapper
import com.example.data.base.ModelEntity
import com.example.domain.model.User
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "USER", indices = [Index("id", unique = true)])
data class UserEntity @Inject constructor(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id") var id: String,
    @ColumnInfo(name = "name")
    @SerializedName("login") var name: String,
    @ColumnInfo(name = "avatar")
    @SerializedName("avatar_url") var avatar: String
) : ModelEntity()

class UserEntityMapper @Inject constructor() : EntityMapper<User, UserEntity> {
    override fun mapToDomain(entity: UserEntity): User = User(
        id = entity.id,
        name = entity.name,
        avatar = entity.avatar
    )

    override fun mapToEntity(model: User): UserEntity = UserEntity(
        id = model.id,
        name = model.name,
        avatar = model.avatar
    )
}
