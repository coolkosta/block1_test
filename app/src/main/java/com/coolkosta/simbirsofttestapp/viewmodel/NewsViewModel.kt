package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.util.Event
import com.coolkosta.simbirsofttestapp.util.JsonHelper


class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()
    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    private var _currentEvent = MutableLiveData<Event>()
    val currentEvent: LiveData<Event> get() = _currentEvent

    init {
        getEvents()
    }

    private fun getEvents() {
        val inputStream = getApplication<Application>().assets.open("events.json")
        _eventList.value = jsonHelper.getEventsFromJson(inputStream)
    }


    fun filteredList(categories: List<Int>) {
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

