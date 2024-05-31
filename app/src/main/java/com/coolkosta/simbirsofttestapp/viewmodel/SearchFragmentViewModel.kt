package com.coolkosta.simbirsofttestapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.util.Generator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragmentViewModel: ViewModel() {
    private val eventList = MutableLiveData<List<String>>()
    private val nkoList = MutableLiveData<List<String>>()
    private val searchResult = MutableLiveData<List<String>>()

    fun getEventList(): LiveData<List<String>> = eventList
    fun getNkoList(): LiveData<List<String>> = nkoList
    fun getSearchResult(): LiveData<List<String>> = searchResult

    init {
        eventList.value = Generator().generateEventList()
        nkoList.value = Generator().generateNkoList()
    }

    fun search(query: String, list: List<String>){
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            val newList = list.filter { it.contains(query, ignoreCase = true) }
            searchResult.postValue(newList)
        }
    }

}