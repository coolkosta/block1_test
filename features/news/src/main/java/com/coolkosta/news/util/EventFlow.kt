package com.coolkosta.news.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object EventFlow {
    private val eventFlow = MutableStateFlow(0)

    fun publish(event: Int) {
        eventFlow.value = event
    }

    fun getEvents(): Flow<Int> = eventFlow
}