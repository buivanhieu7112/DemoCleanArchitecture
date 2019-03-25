package com.example.democleanarchitecture.model

import org.junit.Before
import org.junit.Test

class RepoItemMapperTest {
    private lateinit var repoItemMapper: RepoItemMapper

    @Before
    fun setup() {
        repoItemMapper = RepoItemMapper()
    }

    @Test
    fun mapPresentationToDomainTest() {
        // generate user entity
        val repoItem = createRepoItem()
        // mapper to domain
        val model = repoItemMapper.mapToDoMain(repoItem)

        assert(model.id == repoItem.id)
        assert(model.name == repoItem.name)
        assert(model.avatar == repoItem.avatar)
    }

    @Test
    fun mapDomainToPresentation() {
        // generate model
        val model = createUser()
        // mapper to entity
        val repoItem = repoItemMapper.mapToPresentation(model)

        assert(model.id == repoItem.id)
        assert(model.name == repoItem.name)
        assert(model.avatar == repoItem.avatar)
    }
}
