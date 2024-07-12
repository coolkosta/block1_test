package com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment

import com.coolkosta.simbirsofttestapp.domain.model.EventEntity

sealed interface NewsEvent {
    data class EventsFiltered(val filterList: List<Int>) : NewsEvent
    data class EventReaded(val eventEntity: EventEntity) : NewsEvent
}

