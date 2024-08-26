package com.coolkosta.news.presentation.screen.newsFilterFragment

import com.coolkosta.news.domain.interactor.CategoryInteractor
import com.coolkosta.news.util.SampleData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsFilterViewModelTest {
    private lateinit var viewModel: NewsFilterViewModel
    private val categoryInteractor = mockk<CategoryInteractor>()
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        coEvery { categoryInteractor.getCategoryList() } returns SampleData.categories
        viewModel = NewsFilterViewModel(categoryInteractor)
    }

    @Test
    fun newsFilterViewModel_getCategories_FetchCategoriesSuccess() = runTest(dispatcher) {
        val expectedData = SampleData.categories
        assertEquals(expectedData, viewModel.state.value.categories)
    }

    @Test
    fun newsFilterViewModel_sendEventFilterCategorySelected_getFilterCategoriesSuccess() {
        val listIds = listOf(1, 2)
        val newsFilterEvent = NewsFilterEvent.FilterCategorySelected(listIds)
        viewModel.sendEvent(newsFilterEvent)
        assertEquals(listIds, viewModel.state.value.filteredList)
    }

    @Test
    fun newsFilterViewModel_sendEventSwitchChanged_addIdToFilteredList() {
            val idCategory = 1
            val isSwitchEnable = true
            val expected = listOf(idCategory)
            viewModel.sendEvent(NewsFilterEvent.SwitchChanged(idCategory, isSwitchEnable))
            assertEquals(expected, viewModel.state.value.filteredList)
        }

    @Test
    fun newsFilterViewModel_sendEventSwitchChanged_removeIdFromFilteredList() {
            val idCategory = 1
            val isSwitchEnable = true
            val expected = emptyList<Int>()
            viewModel.sendEvent(NewsFilterEvent.SwitchChanged(idCategory, isSwitchEnable))
            viewModel.sendEvent(NewsFilterEvent.SwitchChanged(idCategory, !isSwitchEnable))
            assertEquals(expected, viewModel.state.value.filteredList)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }
}