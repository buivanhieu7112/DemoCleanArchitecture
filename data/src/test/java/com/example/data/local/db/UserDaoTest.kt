package com.example.data.local.db

import com.example.data.createUserEntity
import com.example.data.model.UserEntity
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class UserDaoTest : DbTest() {

    @Test
    fun testInsertAndGet() {
        // fake user entity
        val userEntity = createUserEntity()
        db.userDao().insertUser(userEntity)

        // create observer to get users entity
        val testObserver = TestObserver<MutableList<UserEntity>>()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // getUsers
        db.userDao().getUsers().toObservable().subscribe(testObserver)

        // data not null or no error
        var users = mutableListOf<UserEntity>()
        users.add(userEntity)
        testObserver.assertNoErrors()
//        testObserver.assertValue {
//            users == it
//        }
    }

    @Test
    fun findNotExists() {
        val userName = anyString()
        val testObserver = TestObserver<MutableList<UserEntity>>()
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        db.userDao().getUserBySearch(userName).toObservable().subscribe(testObserver)
        testObserver.assertValueCount(0)
    }

    @Test
    fun deleteUser(){
        val testObserver = TestObserver<Completable>()
        testObserver.awaitTerminalEvent(2,TimeUnit.SECONDS)

        db.userDao().deleteAllUser().subscribe(testObserver)
        testObserver.assertValueCount(0)
    }
}
