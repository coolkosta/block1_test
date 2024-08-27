package com.coolkosta.profile.presentation.screen

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.coolkosta.profile.R
import com.coolkosta.profile.presentation.viewmodel.ProfileViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.junit.Before
import org.junit.Test

class ProfileFragmentTest {
    private lateinit var viewModel: ProfileViewModel
    private val sideEffectChannel = Channel<ProfileSideEffect>(Channel.BUFFERED)

    @Before
    fun setup() {
        viewModel = mockk()
        every { viewModel.state } returns (MutableStateFlow(ProfileViewState()))
        every { viewModel.sideEffect } returns sideEffectChannel.receiveAsFlow()

    }

    @Test
    fun testProfileImageDisplays() {
       val scenario = launchFragmentInContainer<ProfileFragment>()

        onView(withId(R.id.profile_iv)).check(matches(isDisplayed()))
    }

}