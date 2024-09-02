package com.coolkosta.simbirsofttestapp.eventDetailFragment

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailFragment
import org.junit.Before
import org.junit.Test

private const val EVENT_DETAIL_KEY = "selected_event_key"

class EventDetailFragmentTest {
    private val event = EventEntity(
        id = 1,
        categoryIds = listOf(1, 2),
        foundation = "Фонд 'Подари жизнь'",
        title = "Благотворительный концерт",
        description = "Приглашаем всех на благотворительный концерт. Все собранные средства пойдут на лечение детей.",
        date = "2024-04-20",
        location = "Москва, Крокус Сити Холл",
        contactInfo = "+7 (999) 354-34-24",
        imageName = "image_child"
    )

    @Before
    fun setup() {

        val bundle = Bundle().apply {
            putParcelable(
                EVENT_DETAIL_KEY,
                event
            )
        }
        launchFragmentInContainer<EventDetailFragment>(
            fragmentArgs = bundle,
            themeResId = com.coolkosta.core.R.style.Theme_SimbirSoftTestApp,
        )
    }

    @Test
    fun testDisplayEvent() {
        onView(withId(com.coolkosta.news.R.id.event_title_tv)).check(matches(isDisplayed()))
        onView(withText(event.foundation)).check(matches(isDisplayed()))
        onView(withText(event.location)).check(matches(isDisplayed()))
        onView(withText(event.contactInfo)).check(matches(isDisplayed()))
        onView(withText(event.description)).check(matches(isDisplayed()))
    }

    @Test
    fun testDonationDialogIsClickable() {
        onView(withId(com.coolkosta.news.R.id.donation)).perform(click())
        onView(withId(com.coolkosta.news.R.id.dialog_title_tv)).check(matches(isDisplayed()))
        onView(withId(com.coolkosta.news.R.id.amount_500_btn)).check(matches(isClickable()))
        onView(withId(com.coolkosta.news.R.id.amount_500_btn)).perform(click())
        onView(withId(com.coolkosta.news.R.id.send_button)).check(matches(isClickable()))
        onView(withId(com.coolkosta.news.R.id.send_button)).perform(click())
        onView(withId(com.coolkosta.news.R.id.dialog_title_tv)).check(doesNotExist())
    }

    @Test
    fun testDonationDialogEditText() {
        val inputText = "450"
        onView(withId(com.coolkosta.news.R.id.donation)).perform(click())
        onView(withId(com.coolkosta.news.R.id.dialog_title_tv)).check(matches(isDisplayed()))
        onView(withId(com.coolkosta.news.R.id.sum_edit_text)).perform(typeText(inputText))
            .perform(closeSoftKeyboard())
        onView(withId(com.coolkosta.news.R.id.sum_edit_text)).check(matches(withText(inputText)))
        onView(withId(com.coolkosta.news.R.id.send_button)).perform(click())
        onView(withId(com.coolkosta.news.R.id.dialog_title_tv)).check(doesNotExist())
    }

}