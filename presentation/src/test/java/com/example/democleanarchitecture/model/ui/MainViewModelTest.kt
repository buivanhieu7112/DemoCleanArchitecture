package com.example.democleanarchitecture.model.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.democleanarchitecture.model.*
import com.example.democleanarchitecture.rx.AppSchedulerProvider
import com.example.democleanarchitecture.ui.MainViewModel
import com.example.domain.usecase.user.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var findUserLocalUseCase: FindUserLocalUseCase

    @Mock
    private lateinit var findUserOnlineUseCase: FindUserOnlineUseCase

    @Mock
    private lateinit var saveUserUseCase: SaveUserUseCase

    @Mock
    private lateinit var deleteAllUserLocalUseCase: DeleteAllUserLocalUseCase

    @Mock
    private lateinit var showListUserLocalUseCase: ShowListUserLocalUseCase

    @Mock
    private lateinit var showListUserOnlineUseCase: ShowListUserOnlineUseCase

    @Rule
    @JvmField
    val rxSchedulersOverrideRule: RxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val repoItemMapper = RepoItemMapper()

    private val schedulerProvider = AppSchedulerProvider()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        mainViewModel = MainViewModel(
            findUserLocalUseCase,
            findUserOnlineUseCase,
            saveUserUseCase,
            deleteAllUserLocalUseCase,
            showListUserLocalUseCase,
            showListUserOnlineUseCase,
            repoItemMapper,
            schedulerProvider
        )
    }

    @Test
    fun testGetUsersOnline() {
        val listUser = mutableListOf(createUser())
        given(showListUserOnlineUseCase.createObservable()).willReturn(Flowable.just(listUser))
        mainViewModel.getUsers()
        assertNotNull(mainViewModel.data)
    }

    @Test
    fun testGetUsersLocal() {
        val name = "van"
        val user = createUser()
        val listUser = mutableListOf(user)
        given(findUserLocalUseCase.createObservable(FindUserLocalUseCase.Params(name = name)))
            .willReturn(Flowable.just(listUser))

        mainViewModel.getUserLocalBySearch(name)
        assertEquals(mainViewModel.data.value, listUser.map { repoItemMapper.mapToPresentation(it) })
    }

    @Test
    fun testSaveUserComplete() {
        val user = createUser()
        given(saveUserUseCase.createObservable(SaveUserUseCase.Params(user = user)))
            .willReturn(Completable.complete())
        val test = saveUserUseCase.createObservable(SaveUserUseCase.Params(user = user)).test()
        test.assertComplete()
    }

    @Test
    fun testFindUserOnline() {
        val name = "van"
        val user = createUser()
        val listUser = mutableListOf(user)

        // mô tả, được xem như điều kiện trước khi thực hiện hành vi chỉ định
        given(findUserOnlineUseCase.createObservable(FindUserOnlineUseCase.Params(name = name)))
            .willReturn(Flowable.just(listUser))
        // hành vi chỉ định
        val observer = mock<Observer<List<RepoItem>>>()
        mainViewModel.data.observeForever(observer)
        mainViewModel.getUserBySearch(name)
        // mô tả những thay đổi mong đợi
        assertEquals(mainViewModel.data.value, listUser.map { repoItemMapper.mapToPresentation(it) })
    }

    @Test
    fun testFindUserLocal() {
        val name = "vab"
        val users = mutableListOf(createUser())
        given(findUserLocalUseCase.createObservable(FindUserLocalUseCase.Params(name))).willReturn(Flowable.just(users))
        mainViewModel.getUserLocalBySearch(name)
        assertEquals(mainViewModel.data.value, users.map { repoItemMapper.mapToPresentation(it) })
    }

}
