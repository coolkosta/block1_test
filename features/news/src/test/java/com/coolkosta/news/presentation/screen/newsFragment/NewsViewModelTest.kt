package com.coolkosta.news.presentation.screen.newsFragment

import com.coolkosta.news.domain.interactor.CategoryInteractor
import com.coolkosta.news.domain.interactor.EventInteractor
import com.coolkosta.news.util.EventFlow
import com.coolkosta.news.util.SampleData
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class NewsViewModelTest {
    private lateinit var viewModel: NewsViewModel
    private val eventInteractor = mockk<EventInteractor>()
    private val categoryInteractor = mockk<CategoryInteractor>()
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        coEvery { eventInteractor.getEventList() } returns SampleData.events
        coEvery { categoryInteractor.getCategoryList() } returns SampleData.categories
        viewModel = NewsViewModel(eventInteractor, categoryInteractor)
        mockkObject(EventFlow)
        every { EventFlow.publish(any()) } just Runs
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun newsViewModel_fetchNews_updateStateSuccess() = runTest(dispatcher) {
        advanceUntilIdle()
        val expectedState = NewsState.Success(
            eventEntities = SampleData.events,
            filterCategories = SampleData.categories.map { it.id }
        )
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun newsViewModel_sendEventEventsFiltered_getFilteredList() {
        val filteredList = listOf(1, 2)
        val event = NewsEvent.EventsFiltered(filteredList)
        viewModel.sendEvent(event)

        assertEquals(filteredList, (viewModel.state.value as NewsState.Success).filterCategories)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun newsViewModel_sendEventReadEvent_countDecrease() = runTest(dispatcher) {
        val event = SampleData.events[0]
        val newsEvent = NewsEvent.EventReaded(event)
        advanceUntilIdle()
        verify { EventFlow.publish(2) }
        viewModel.sendEvent(newsEvent)
        verify { EventFlow.publish(1) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }
}