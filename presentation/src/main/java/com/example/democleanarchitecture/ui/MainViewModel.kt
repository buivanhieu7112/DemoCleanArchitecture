package com.example.democleanarchitecture.ui

import android.util.Log
import com.example.democleanarchitecture.base.BaseViewModel
import com.example.democleanarchitecture.model.RepoItem
import com.example.democleanarchitecture.model.RepoItemMapper
import com.example.domain.usecase.user.DatabaseUseCase
import com.example.domain.usecase.user.GetUsersUseCase
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val databaseUseCase: DatabaseUseCase,
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
                response.map { itemMapper.mapToPresentation(it) }.toMutableList()
            }
            .subscribe({ response ->
                mainAdapter.submitList(response)
                Log.d("DATA_SUCCESS", response.size.toString())
            }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun getUserBySearch(name: String) {
        val disposable = getUsersUseCase.getUserBySearch(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                response.map { itemMapper.mapToPresentation(it) }.toMutableList()
            }
            .subscribe({ response -> mainAdapter.submitList(response) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun insertUserToLocal(user: RepoItem) {
        databaseUseCase.insertUser(itemMapper.mapToDoMain(user))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        val disposable = databaseUseCase.getUsersLocal()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response -> response.map { itemMapper.mapToPresentation(it) } }
            .subscribe({ response -> mainAdapter.submitList(response) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }

    fun getUserLocalBySearch(name: String) {
        val disposable = databaseUseCase.getUserLocalBySearch(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response -> response.map { itemMapper.mapToPresentation(it) } }
            .subscribe({ response -> mainAdapter.submitList(response) }, { error -> error.localizedMessage })
        launchDisposable(disposable)
    }


    fun deleteAllUser() {
        val disposable = Completable.create { emitter ->
            databaseUseCase.deleteUser()
            emitter.onComplete()
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
        launchDisposable(disposable)
    }
}
