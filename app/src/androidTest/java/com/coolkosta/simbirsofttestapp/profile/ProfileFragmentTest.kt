package com.coolkosta.simbirsofttestapp.profile

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coolkosta.profile.R
import com.coolkosta.profile.presentation.screen.ProfileFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

    @Before
    fun setup() {
        launchFragmentInContainer<ProfileFragment>(
            fragmentArgs = Bundle(),
            themeResId = com.coolkosta.core.R.style.Theme_SimbirSoftTestApp,
        )
    }

    @Test
    fun testProfileImageDisplays() {
        onView(withId(R.id.profile_iv)).check(matches(isDisplayed()))
    }

    @Test
    fun testChooseDialogShown() {
        onView(withId(R.id.profile_iv)).perform(click())
        onView(withText("Выбрать фото")).check(matches(isDisplayed()))
        onView(withText("Сделать снимок")).check(matches(isDisplayed()))
        onView(withText("Удалить")).check(matches(isDisplayed()))
    }

    @Test
    fun testLogoutButtonIsClickable() {
        onView(withId(R.id.exit_profile_btn)).check(matches(isClickable()))
    }
}
