package com.coolkosta.search.presentation.screen

import androidx.lifecycle.ViewModel
import com.coolkosta.search.util.Generator
import com.coolkosta.search.util.SearchCategory

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchByEventFragmentViewModel : ViewModel() {

    private val initList = MutableStateFlow<List<String>>(emptyList())
    private val _searchResult = MutableStateFlow<List<String>>(emptyList())

    val searchResult = _searchResult.asStateFlow()

    fun sendEvent(searchByEventIntent: SearchEvent) {
        when (searchByEventIntent) {
            is SearchEvent.CategorySelected -> {
                setCategory(searchByEventIntent.category)
            }

            is SearchEvent.SearchQueryChanged -> {
                onSearchQueryChanged(searchByEventIntent.query)
            }
        }
    }

    private fun setCategory(category: SearchCategory) {
        initList.value =
            when (category) {
                SearchCategory.EVENTS -> Generator().generateEventList()

                SearchCategory.NKO -> Generator().generateNkoList()
            }
    }

    private fun onSearchQueryChanged(query: String) {
        val newList = initList.value.filter { it.contains(query, ignoreCase = true) }
        _searchResult.value = newList
    }
}