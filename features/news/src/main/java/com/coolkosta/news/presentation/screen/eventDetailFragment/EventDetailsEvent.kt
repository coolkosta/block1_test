package com.coolkosta.news.presentation.screen.eventDetailFragment

import com.coolkosta.news.domain.model.EventEntity

sealed interface EventDetailsEvent {
    data class CurrentEvent(val eventEntity: EventEntity): EventDetailsEvent
    data class DonationTransferred(val donation: Double): EventDetailsEvent
}