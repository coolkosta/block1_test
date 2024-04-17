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

    init {
        getEvents()
        getCategories()
    }

    private fun getEvents() {
        val inputStream = getApplication<Application>().assets.open("events.json")
        _eventList.value = jsonHelper.getEventsFromJson(inputStream)
    }

    private fun getCategories() {
        val inputStream = getApplication<Application>().assets.open("categories.json")
        _categories.value = jsonHelper.getCategoryFromJson(inputStream)
    }

    fun filteredList(categories: List<EventCategory>) {
        val currentEvents = _eventList.value
        val filteredList = currentEvents?.filter { event ->
            categories.any {
                event.categoryIds.contains(it.id)
            }
        }
        _eventList.value = filteredList ?: emptyList()
    }

}

