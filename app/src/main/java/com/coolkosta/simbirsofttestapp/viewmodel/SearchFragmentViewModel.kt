package com.coolkosta.simbirsofttestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchFragmentViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")

    @OptIn(FlowPreview::class)
    val searchText = _searchText
        .asStateFlow()
        .debounce(TIMEOUT)
        .distinctUntilChanged()

    fun onSearchViewTextChanged(text: String) {
        viewModelScope.launch {
            _searchText.value = text
        }
    }

    companion object {
        private const val TIMEOUT = 500L
    }
}