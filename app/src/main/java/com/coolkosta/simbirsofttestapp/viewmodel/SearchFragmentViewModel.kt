package com.coolkosta.simbirsofttestapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce

class SearchFragmentViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val searchText = _searchText
        .asStateFlow()
        .debounce(TIMEOUT)

    fun onSearchViewTextChanged(text: String) {
        _searchText.value = text
    }

    companion object {
        private const val TIMEOUT = 500L
    }
}