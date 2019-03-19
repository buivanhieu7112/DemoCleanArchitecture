package com.example.data.response

import com.example.data.model.UserEntity
import com.google.gson.annotations.SerializedName

data class SearchRepoResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: MutableList<UserEntity>
)
