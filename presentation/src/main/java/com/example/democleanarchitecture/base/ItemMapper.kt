package com.example.democleanarchitecture.base

import com.example.domain.model.Model

interface ItemMapper<M : Model, MI : ModelItem> {
    fun mapToPresentation(model: M): ModelItem

    fun mapToDoMain(modelItem: MI): Model
}
