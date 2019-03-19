package com.example.democleanarchitecture.util

import com.example.democleanarchitecture.model.RepoItem

interface ItemCLickListener {
    fun onItemClicked(user: RepoItem)
}
