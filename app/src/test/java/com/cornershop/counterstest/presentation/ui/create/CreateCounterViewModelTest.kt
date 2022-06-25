package com.cornershop.counterstest.presentation.ui.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cornershop.counterstest.MainCoroutineRule
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.data.request.CreateCounterRequest
import com.cornershop.counterstest.domain.model.Counter
import com.cornershop.counterstest.domain.usecases.CreateCounterUseCase
import com.cornershop.counterstest.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CreateCounterViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var createCounterUseCase: CreateCounterUseCase
    private val repository = mockk<CounterRepositoryImpl>()
    private lateinit var viewModel: CreateCounterViewModel

    @Before
    fun setUp() {
        createCounterUseCase = CreateCounterUseCase(repository)
        viewModel = CreateCounterViewModel(createCounterUseCase)
    }

    @Test
    fun `should show the loading indicator`() {
        viewModel.toggleProgressDialog(true)

        assertEquals(true, viewModel.isLoading.getOrAwaitValue())
    }

    @Test
    fun `should dismiss the loading indicator`() {
        viewModel.toggleProgressDialog(false)

        assertEquals(false, viewModel.isLoading.getOrAwaitValue())
    }

    @Test
    fun `given a name it should save a counter to the server successfully`() = mainCoroutineRule.runBlockingTest {
        val name = "test"
        val expected = listOf(Counter("id", name))
        val repositoryResponse = Response.success(200, listOf(Counter("id", name)))

        coEvery { repository.createCounter(CreateCounterRequest(name)) } returns repositoryResponse

        viewModel.saveCounter(name)

        val result = viewModel.save.getOrAwaitValue()

        assertTrue(result.isSuccess)
        assertEquals(expected, result.data)
    }

}