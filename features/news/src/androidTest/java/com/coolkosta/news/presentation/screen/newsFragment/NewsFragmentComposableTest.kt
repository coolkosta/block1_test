package com.coolkosta.news.presentation.screen.newsFragment

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.coolkosta.core.presentation.ui.theme.SimbirSoftTestAppTheme
import com.coolkosta.news.util.SampleData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsFragmentComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: NewsViewModel
    private val sideEffectChannel = Channel<NewsSideEffect>(Channel.BUFFERED)

    @Before
    fun setup() {
        viewModel = mockk()
        every { viewModel.state } returns MutableStateFlow(
            NewsState.Success(
                SampleData.events,
                SampleData.categories.map { it.id })
        )
        every { viewModel.sideEffect } returns sideEffectChannel.receiveAsFlow()
        composeTestRule.setContent {
            SimbirSoftTestAppTheme {
                NewsScreen(newsViewModel = viewModel, onCLick = { }) {
                }
            }
        }
    }

    @Test
    fun testTopAppBarTitle() {
        composeTestRule.onNodeWithText("Новости").assertIsDisplayed()
    }

    @Test
    fun testFilterIconClick() {
        composeTestRule.onNodeWithContentDescription("Filter fragment").assertHasClickAction()

    }

    @Test
    fun testEventClick() {
        composeTestRule.onNodeWithText("Благотворительный концерт").assertHasClickAction()
    }
}