package com.shashavs.data.network

import com.shashavs.data.TestDto
import com.shashavs.data.TestJson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ProductsServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: ProductsService
    private val json = Json { ignoreUnknownKeys = true }
    private val skip = 0
    private val limit = 30

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ProductsService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getProducts successful response`() = runTest {
        val mockBody = TestJson.response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockBody)
        )

        val response = service.getProducts(skip, limit)
        val recordedRequest = mockWebServer.takeRequest()

        assertEquals("/products?skip=$skip&limit=$limit", recordedRequest.path)
        assertEquals("GET", recordedRequest.method)
        assertEquals(TestDto.productsResponseDto, response.body())
    }

}