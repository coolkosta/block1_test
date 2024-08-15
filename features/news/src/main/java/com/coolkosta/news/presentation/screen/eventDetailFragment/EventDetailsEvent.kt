package com.coolkosta.news.presentation.screen.eventDetailFragment

import com.coolkosta.news.domain.model.EventEntity

sealed interface EventDetailsEvent {
    data class CurrentEvent(val eventEntity: EventEntity): EventDetailsEvent
    data class DonationQueryChanged(val donation: Int):EventDetailsEvent
    data object DonationTransferred: EventDetailsEvent
}