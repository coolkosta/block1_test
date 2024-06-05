package com.coolkosta.simbirsofttestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.util.CoroutineExceptionHandler
import com.coolkosta.simbirsofttestapp.util.Generator
import com.coolkosta.simbirsofttestapp.util.SearchCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchByEventFragmentViewModel : ViewModel() {


    private val _initList = MutableStateFlow<List<String>>(emptyList())
    private val _searchResult = MutableStateFlow<List<String>>(emptyList())

    val initList get() = _initList.value
    val searchResult = _searchResult.asStateFlow()

    fun getInitList(category: SearchCategory) {
        when (category) {
            SearchCategory.EVENTS -> {
                _initList.value = Generator().generateEventList()
            }

            SearchCategory.NKO -> {
                _initList.value = Generator().generateNkoList()
            }
        }
    }

    fun search(
        query: String,
        list: List<String>,
        dispatcher: CoroutineDispatcher = Dispatchers.Default
    ) {
        val coroutineException = CoroutineExceptionHandler.createCoroutineExceptionHandler(
            EXCEPTION_TAG
        ) {
            _searchResult.value = emptyList()
        }
        viewModelScope.launch(coroutineException) {
            withContext(dispatcher) {
                delay(TIMEOUT)
                val newList = list.filter { it.contains(query, ignoreCase = true) }
                _searchResult.value = newList
            }
        }
    }

    companion object {
        private const val TIMEOUT = 500L
        private const val EXCEPTION_TAG = "Search_by_event_fragment"
    }
}