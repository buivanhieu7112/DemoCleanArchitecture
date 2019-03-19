package com.example.democleanarchitecture.ui

import android.util.Log
import com.example.democleanarchitecture.base.BaseViewModel
import com.example.domain.usecase.user.GetUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {
    fun getUsers() {
        val disposable = getUsersUseCase.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response -> Log.d("DATA_SUCCESS", response.size.toString()) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }
}
