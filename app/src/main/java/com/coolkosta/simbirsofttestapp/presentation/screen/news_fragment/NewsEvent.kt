package com.coolkosta.simbirsofttestapp.presentation.screen.news_fragment

import com.coolkosta.simbirsofttestapp.domain.model.Event

sealed class NewsEvent {
    data class FilteredEvents(val filterList: List<Int>) : NewsEvent()
    data class EventReader(val event: Event) : NewsEvent()
}

