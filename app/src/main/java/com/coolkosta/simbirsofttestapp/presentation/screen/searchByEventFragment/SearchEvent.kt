package com.coolkosta.simbirsofttestapp.presentation.screen.searchByEventFragment

import com.coolkosta.simbirsofttestapp.util.SearchCategory

sealed interface SearchEvent {
    data class CategorySelected(val category: SearchCategory) : SearchEvent
    data class SearchQueryChanged(val query: String) : SearchEvent
}