package com.coolkosta.simbirsofttestapp.presentation

import com.coolkosta.simbirsofttestapp.domain.model.Event

sealed class NewsState {
    data object Loading : NewsState()
    data class Success(val events: List<Event>) : NewsState()
    data class Error(val error: Throwable) : NewsState()
}