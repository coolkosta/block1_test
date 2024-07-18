package com.coolkosta.news.presentation.screen.newsFragment

import com.coolkosta.news.domain.model.EventEntity


sealed interface NewsEvent {
    data class EventsFiltered(val filterList: List<Int>) : NewsEvent
    data class EventReaded(val eventEntity: EventEntity) : NewsEvent
}

