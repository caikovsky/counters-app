package com.cornershop.counterstest.presentation.ui.create

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cornershop.counterstest.MainCoroutineRule
import com.cornershop.counterstest.data.repository.CounterRepositoryImpl
import com.cornershop.counterstest.domain.usecases.CreateCounterUseCase
import com.cornershop.counterstest.getOrAwaitValue
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
}
