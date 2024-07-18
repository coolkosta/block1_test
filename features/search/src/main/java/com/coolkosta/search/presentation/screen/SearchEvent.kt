package com.coolkosta.search.presentation.screen

import com.coolkosta.search.util.SearchCategory

sealed interface SearchEvent {
    data class CategorySelected(val category: SearchCategory) : SearchEvent
    data class SearchQueryChanged(val query: String) : SearchEvent
}