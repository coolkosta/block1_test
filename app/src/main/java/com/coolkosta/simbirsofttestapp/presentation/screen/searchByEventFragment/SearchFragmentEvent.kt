package com.coolkosta.simbirsofttestapp.presentation.screen.searchByEventFragment

sealed interface SearchFragmentEvent {
    data class SearchViewTextChanged(val text: String) : SearchFragmentEvent
}