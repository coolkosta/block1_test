package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.util.Event
import com.coolkosta.simbirsofttestapp.util.EventCategory
import com.coolkosta.simbirsofttestapp.util.JsonHelper


class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {


    private val jsonHelper = JsonHelper()
    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    private var _categories = MutableLiveData<List<EventCategory>>()
    val categories: LiveData<List<EventCategory>> get() = _categories

    private var _currentEvent = MutableLiveData<Event>()
    val currentEvent: LiveData<Event> get() = _currentEvent

    private var _filteredCategories = MutableLiveData<MutableList<Int>>()
    val filteredCategories: LiveData<MutableList<Int>> get() = _filteredCategories
    private var tempFilterCategory = mutableListOf<Int>()

    init {
        getEvents()
        getCategories()
        _filteredCategories.value = _categories.value?.map { it.id } as MutableList<Int>?
    }

    fun onSwitchChanged(idCategory: Int, isSwitchEnable: Boolean) {
        val currentFilteredCategories = _filteredCategories.value
        if (isSwitchEnable) {
            if (currentFilteredCategories?.contains(idCategory) != true) {
                currentFilteredCategories?.add(idCategory)
            }
        } else {
            currentFilteredCategories?.remove(idCategory)
        }

        tempFilterCategory = currentFilteredCategories ?: mutableListOf()
    }

    private fun getEvents() {
        val inputStream = getApplication<Application>().assets.open("events.json")
        _eventList.value = jsonHelper.getEventsFromJson(inputStream)
    }

    private fun getCategories() {
        val inputStream = getApplication<Application>().assets.open("categories.json")
        _categories.value = jsonHelper.getCategoryFromJson(inputStream)
    }

    fun filteredList(categories: List<Int>) {
        _filteredCategories.value = tempFilterCategory
        getEvents()
        val currentEvents = _eventList.value
        val filteredList = currentEvents?.filter { event ->
            categories.any {
                event.categoryIds.contains(it)
            }
        }
        _eventList.value = filteredList ?: emptyList()
    }

    fun getCurrentEvent(event: Event) {
        _currentEvent.value = event
    }

}

