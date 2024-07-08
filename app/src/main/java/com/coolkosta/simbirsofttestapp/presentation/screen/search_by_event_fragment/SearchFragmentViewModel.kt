package com.coolkosta.simbirsofttestapp.presentation.screen.search_by_event_fragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

class SearchFragmentViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val searchText = _searchText
        .asStateFlow()
        .debounce(TIMEOUT)
        .distinctUntilChanged()

    fun sendEvent(searchFragmentIntent: SearchFragmentIntent) {
        when (searchFragmentIntent) {
            is SearchFragmentIntent.SearchView -> {
                onSearchViewTextChanged(searchFragmentIntent.text)
            }
        }
    }

   private fun onSearchViewTextChanged(text: String) {
        _searchText.value = text
    }

    companion object {
        private const val TIMEOUT = 500L
    }
}