package com.example.democleanarchitecture.ui

import android.util.Log
import com.example.democleanarchitecture.base.BaseViewModel
import com.example.democleanarchitecture.model.RepoItemMapper
import com.example.domain.usecase.user.GetUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val itemMapper: RepoItemMapper
) : BaseViewModel() {
    private lateinit var mainAdapter: MainAdapter

    fun getAdapter(adapter: MainAdapter) {
        mainAdapter = adapter
    }

    fun getUsers() {
        val disposable = getUsersUseCase.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                response.map {
                    itemMapper.mapToPresentation(it)
                }.toMutableList()
            }
            .subscribe({ response ->
                mainAdapter.submitList(response)
                Log.d("DATA_SUCCESS", response.size.toString())
            }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }
}
