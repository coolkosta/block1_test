package com.coolkosta.search.presentation.screen

sealed interface SearchFragmentEvent {
    data class SearchViewTextChanged(val text: String) : SearchFragmentEvent
}