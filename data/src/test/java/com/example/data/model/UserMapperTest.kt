package com.example.data.model

import com.example.data.createUser
import com.example.data.createUserEntity
import org.junit.Before
import org.junit.Test

class UserMapperTest {
    private lateinit var userEntityMapper: UserEntityMapper

    @Before
    fun setup() {
        userEntityMapper = UserEntityMapper()
    }

    @Test
    fun mapEntityToDomainTest() {
        // generate user entity
        val entity = createUserEntity()
        // mapper
        val model = userEntityMapper.mapToDomain(entity)
        assert(entity.id == model.id)
        assert(entity.name == model.name)
        assert(entity.avatar == model.avatar)
    }

    @Test
    fun mapDomainToEntityTest() {
        // generate model
        val user = createUser()
        // mapper
        val entity = userEntityMapper.mapToEntity(user)
        assert(entity.id == user.id)
        assert(entity.name == user.name)
        assert(entity.avatar == user.avatar)
    }
}
