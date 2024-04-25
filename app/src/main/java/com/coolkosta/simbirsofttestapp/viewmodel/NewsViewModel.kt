package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.entity.EventCategory
import com.coolkosta.simbirsofttestapp.util.JsonHelper


class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()
    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    var filterCategories: List<Int> = listOf()

    init {
        getEvents()
        filterCategories = getCategories().map { it.id }
    }

    private fun getEvents() {
        val streamResult = getApplication<Application>().assets.open("events.json").use {
            jsonHelper.getEventsFromJson(it)
        }
        _eventList.value = streamResult
    }

    private fun getCategories(): List<EventCategory> {
        getApplication<Application>().assets.open("categories.json").use {
            return jsonHelper.getCategoryFromJson(it)
        }
    }

    fun onCategoriesChanged(categories: List<Int>) {
        filterCategories = categories
        filteredList()
    }

    private fun filteredList() {
        getEvents()
        _eventList.value = _eventList.value.orEmpty().filter { event ->
            filterCategories.any {
                event.categoryIds.contains(it)
            }
        }
    }
}

