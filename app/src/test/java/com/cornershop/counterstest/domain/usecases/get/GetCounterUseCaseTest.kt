package com.cornershop.counterstest.domain.usecases.get

import com.cornershop.counterstest.MainCoroutineRule
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.model.Counter
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class GetCounterUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository = mockk<CounterRepositoryImpl>()
    private lateinit var getCounterUseCase: GetCounterUseCase

    @Before
    fun setUp() {
        getCounterUseCase = GetCounterUseCase(repository)
    }

    @Test
    fun `should call repository and return a list of counter successfully`() = mainCoroutineRule.runBlockingTest {
        val repositoryResponse = Response.success(200, listOf(Counter("id")))

        coEvery { repository.getCounters() } returns repositoryResponse

        val result = getCounterUseCase.invoke()

        assertNotNull(result.data)
        assertTrue(result.isSuccess)
        assertEquals(repositoryResponse.body(), result.data)
    }

    @Test
    fun `should call repository and return an error`() = mainCoroutineRule.runBlockingTest {
        val repositoryResponse = Response.error<List<Counter>>(400, "error".toResponseBody("application/json".toMediaTypeOrNull()))

        coEvery { repository.getCounters() } returns repositoryResponse

        val result = getCounterUseCase.invoke()

        assertNull(result.data)
        assertTrue(result.isError)
        assertEquals(repositoryResponse.body(), result.data)
    }
}