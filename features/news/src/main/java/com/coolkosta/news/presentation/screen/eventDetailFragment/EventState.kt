package com.coolkosta.news.presentation.screen.eventDetailFragment

import com.coolkosta.news.domain.model.EventEntity

data class EventState(
    val event: EventEntity? = null,
    val currentAmount: Int = 0,
    val isEnabled: Boolean = false
)

