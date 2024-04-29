package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.entity.EventCategory
import com.coolkosta.simbirsofttestapp.util.JsonHelper
import java.util.concurrent.Callable
import java.util.concurrent.Executors


class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()
    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    var filterCategories: List<Int> = listOf()

    private var initList = MutableLiveData<List<Event>>()

    init {
        if (_eventList.value == null) {
            eventExecutor()
        }

        filterCategories = getCategories().map { it.id }
    }

    private fun getEvents() {
        val streamResult = getApplication<Application>().assets.open("events.json").use {
            jsonHelper.getEventsFromJson(it)
        }
        _eventList.postValue(streamResult)
        initList.postValue(streamResult)
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
        _eventList.value = initList.value?.filter { event ->
            filterCategories.any {
                event.categoryIds.contains(it)
            }
        }
    }

    private fun eventExecutor() {
        val callable = Callable {
            Thread.sleep(5000)
            getEvents()
        }
        Executors.newSingleThreadExecutor().submit(callable)
    }
}

