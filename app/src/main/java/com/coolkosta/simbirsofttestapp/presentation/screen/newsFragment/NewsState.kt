package com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment

import com.coolkosta.simbirsofttestapp.domain.model.EventEntity

sealed interface NewsState {
    data object Loading : NewsState

    data class Success(
        val eventEntities: List<EventEntity>,
        val filterCategories: List<Int>
    ) : NewsState

    data object Error : NewsState
}