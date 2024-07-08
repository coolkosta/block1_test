package com.coolkosta.simbirsofttestapp.presentation.screen.search_by_event_fragment

sealed class SearchFragmentIntent {
    data class SearchView(val text: String) : SearchFragmentIntent()
}