package com.coolkosta.simbirsofttestapp.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object EventFlow {
    private val eventFlow = MutableStateFlow<Int>(0)

    fun publish(event: Int) {
        eventFlow.value = event
    }

    fun listen(): Flow<Int> = eventFlow
}