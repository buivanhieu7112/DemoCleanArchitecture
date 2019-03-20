package com.example.democleanarchitecture.ui

import android.util.Log
import com.example.democleanarchitecture.base.BaseViewModel
import com.example.democleanarchitecture.model.RepoItem
import com.example.democleanarchitecture.model.RepoItemMapper
import com.example.democleanarchitecture.rx.AppSchedulerProvider
import com.example.domain.usecase.user.*
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val findUserLocalUseCase: FindUserLocalUseCase,
    private val findUserOnlineUseCase: FindUserOnlineUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteAllUserLocalUseCase: DeleteAllUserLocalUseCase,
    private val showListUserLocalUseCase: ShowListUserLocalUseCase,
    private val showListUserOnlineUseCase: ShowListUserOnlineUseCase,
    private val itemMapper: RepoItemMapper,
    private val schedulerProvider: AppSchedulerProvider
) : BaseViewModel() {
    private lateinit var mainAdapter: MainAdapter

    fun getAdapter(adapter: MainAdapter) {
        mainAdapter = adapter
    }

    fun getUsers() {
        val disposable = showListUserOnlineUseCase.createObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map { response ->
                response.map { itemMapper.mapToPresentation(it) }.toMutableList()
            }.subscribe({ response ->
                mainAdapter.submitList(response)
                Log.d("DATA_SUCCESS", response.size.toString())
            }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun getUserBySearch(name: String) {
        val disposable = findUserOnlineUseCase.createObservable(FindUserOnlineUseCase.Params(name = name))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map { response ->
                response.map { itemMapper.mapToPresentation(it) }.toMutableList()
            }
            .subscribe({ response -> mainAdapter.submitList(response) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun insertUserToLocal(user: RepoItem) {
        saveUserUseCase.createObservable(SaveUserUseCase.Params(user = itemMapper.mapToDoMain(user)))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    Log.d("DATABASE", "INSERT_SUCCESS")
                }

                override fun onSubscribe(d: Disposable) {
                    launchDisposable(d)
                }

                override fun onError(e: Throwable) {
                    e.localizedMessage
                }
            })
    }

    fun getUsersLocal() {
        val disposable = showListUserLocalUseCase.createObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map { response -> response.map { itemMapper.mapToPresentation(it) } }
            .subscribe({ response -> mainAdapter.submitList(response) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun getUserLocalBySearch(name: String) {
        val disposable = findUserLocalUseCase.createObservable(FindUserLocalUseCase.Params(name = name))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .map { response -> response.map { itemMapper.mapToPresentation(it) } }
            .subscribe({ response -> mainAdapter.submitList(response) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun deleteAllUser() {
        deleteAllUserLocalUseCase.createObservable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(
                object : CompletableObserver {
                    override fun onComplete() {
                        Log.d("DATABASE", "DELETE_ALL_SUCCESS")
                    }

                    override fun onSubscribe(d: Disposable) {
                        launchDisposable(d)
                    }

                    override fun onError(e: Throwable) {
                        e.localizedMessage
                    }
                })
    }
}
