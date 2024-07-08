package com.coolkosta.simbirsofttestapp.presentation.screen.news_fragment

import com.coolkosta.simbirsofttestapp.domain.model.Event

sealed class NewsState {
    data object Loading : NewsState()
    data class Success(val events: List<Event>, val filterCategories: List<Int>) : NewsState()
    data class Error(val error: Throwable) : NewsState()
}