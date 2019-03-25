package com.example.data.remote

import com.example.data.remote.api.ApiService
import com.example.data.response.SearchRepoResponse
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserApiTest {

    private lateinit var userApi: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        userApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testMockReponse() {
        val mockResponse = MockResponse()

        mockResponse.setResponseCode(200)
        mockResponse.setBody("{}")

        mockWebServer.enqueue(mockResponse)
    }

    @Test
    fun testResponseOk() {
        val testObserver = TestObserver<SearchRepoResponse>()
        val userName = anyString()

        val path = "/search/users?q=$userName"
        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
//            .setBody(getJson("search.json"))

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        //Call Api
        userApi.getUserBySearch(userName).toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // then no error
        testObserver.assertNoErrors()
        // check values
        testObserver.assertValueCount(1)

        // get request to test
        val request = mockWebServer.takeRequest()

        assertEquals(request.path, path)

        // test success value by search.json
        testObserver.assertValue { searchRepoResponse: SearchRepoResponse ->
            searchRepoResponse.total == 1
                    && searchRepoResponse.items[0].id == "46143615"
        }
    }
}
