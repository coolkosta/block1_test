package com.coolkosta.news.presentation.screen.newsFragment

import com.coolkosta.news.domain.interactor.CategoryInteractor
import com.coolkosta.news.domain.interactor.EventInteractor
import com.coolkosta.news.domain.model.CategoryEntity
import com.coolkosta.news.domain.model.EventEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
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

    val events = listOf(
        EventEntity(
            id = 1,
            categoryIds = listOf(1, 2),
            foundation = "Фонд 'Подари жизнь'",
            title = "Благотворительный концерт",
            description = "Приглашаем всех на благотворительный концерт. Все собранные средства пойдут на лечение детей.",
            date = "2024-04-20",
            location = "Москва, Крокус Сити Холл",
            contactInfo = "+7 (999) 354-34-24",
            imageName = "image_child"

        ),
        EventEntity(
            id = 2,
            categoryIds = listOf(2),
            foundation = "Фонд 'Нужна помощь'",
            title = "Сбор средств для детей",
            description = "Организуем сбор средств для помощи детям с редкими заболеваниями. Каждый рубль на счету!",
            date = "2024-05-29",
            location = "Санкт-Петербург, Дворцовая площадь",
            contactInfo = "+7 (999) 354-34-24",
            imageName = "image_kid"
        )
    )
    val categories = listOf(
        CategoryEntity(
            id = 1,
            title = "Благотворительность"
        ),
        CategoryEntity(
            id = 2,
            title = "Дети"
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        coEvery { eventInteractor.getEventList() } returns events
        coEvery { categoryInteractor.getCategoryList() } returns categories
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun newsViewModel_fetchNews_updateStateSuccess() = runTest(dispatcher) {
        viewModel = NewsViewModel(eventInteractor, categoryInteractor)
        advanceUntilIdle()

        val expectedState = NewsState.Success(
            eventEntities = events,
            filterCategories = categories.map { it.id }
        )
        assertEquals(expectedState, viewModel.state.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun newsViewModel_fetchNews_updateStateError() = runTest(dispatcher) {
      coEvery { eventInteractor.getEventList()} throws CancellationException()
         viewModel = NewsViewModel(eventInteractor, categoryInteractor)
        advanceUntilIdle()
        assertEquals(NewsState.Error, viewModel.state.value)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }
}