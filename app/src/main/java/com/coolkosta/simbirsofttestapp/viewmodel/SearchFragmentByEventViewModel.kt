package com.coolkosta.simbirsofttestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.util.Generator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragmentByEventViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<String>>()
    private val _nkoList = MutableLiveData<List<String>>()
    private val _searchResult = MutableLiveData<List<String>>()

    val eventList get() = _eventList.value
    val nkoList get() = _nkoList.value
    val searchResult: LiveData<List<String>> get() = _searchResult

    fun generateEventList() {
        _eventList.value = Generator().generateEventList()
    }

    fun generateNkoList() {
        _nkoList.value = Generator().generateNkoList()
    }

    fun search(
        query: String,
        list: List<String>,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(dispatcher) {
            delay(500)
            val newList = list.filter { it.contains(query, ignoreCase = true) }
            _searchResult.postValue(newList)
        }
    }

}