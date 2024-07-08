package com.coolkosta.simbirsofttestapp.presentation.screen.search_by_event_fragment

import com.coolkosta.simbirsofttestapp.util.SearchCategory

sealed class SearchByEventIntent {
    data class Category(val category: SearchCategory) : SearchByEventIntent()
    data class SearchQuery(val query: String) : SearchByEventIntent()
}