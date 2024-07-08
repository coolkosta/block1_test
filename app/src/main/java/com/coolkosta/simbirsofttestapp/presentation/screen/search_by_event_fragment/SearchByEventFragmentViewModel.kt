package com.coolkosta.simbirsofttestapp.presentation.screen.search_by_event_fragment

import androidx.lifecycle.ViewModel
import com.coolkosta.simbirsofttestapp.util.Generator
import com.coolkosta.simbirsofttestapp.util.SearchCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchByEventFragmentViewModel : ViewModel() {

    private val initList = MutableStateFlow<List<String>>(emptyList())
    private val _searchResult = MutableStateFlow<List<String>>(emptyList())

    val searchResult = _searchResult.asStateFlow()

    fun setCategory(category: SearchCategory) {
        initList.value =
            when (category) {
                SearchCategory.EVENTS -> Generator().generateEventList()

                SearchCategory.NKO -> Generator().generateNkoList()
            }
    }

    fun onSearchQueryChanged(query: String) {
        val newList = initList.value.filter { it.contains(query, ignoreCase = true) }
        _searchResult.value = newList
    }
}