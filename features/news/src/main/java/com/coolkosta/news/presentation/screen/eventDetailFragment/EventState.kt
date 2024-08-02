package com.coolkosta.news.presentation.screen.eventDetailFragment

import com.coolkosta.news.domain.model.EventEntity

sealed interface EventState {
    data object Loading : EventState
    data class Success(
        val event: EventEntity,
        val donation: Double = 0.00
    ) : EventState
}

