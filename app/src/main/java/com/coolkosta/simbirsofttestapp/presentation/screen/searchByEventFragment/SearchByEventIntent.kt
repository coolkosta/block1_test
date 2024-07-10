package com.coolkosta.simbirsofttestapp.presentation.screen.searchByEventFragment

import com.coolkosta.simbirsofttestapp.util.SearchCategory

sealed interface SearchByEventIntent {
    data class CategorySelected(val category: SearchCategory) : SearchByEventIntent
    data class SearchQueryChanged(val query: String) : SearchByEventIntent
}