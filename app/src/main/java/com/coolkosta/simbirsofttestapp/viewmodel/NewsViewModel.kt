package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.entity.EventCategory
import com.coolkosta.simbirsofttestapp.util.CoroutineExceptionHandler
import com.coolkosta.simbirsofttestapp.util.EventFlow
import com.coolkosta.simbirsofttestapp.util.JsonHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()
    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    val progress = MutableLiveData(true)

    var filterCategories: List<Int> = listOf()
    private var initList: List<Event> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    init {
        fetchData()
    }

    private suspend fun getEvents(): List<Event> {
        return withContext(Dispatchers.IO) {
            delay(TIMEOUT)
            getApplication<Application>().assets.open("events.json").use {
                jsonHelper.getEventsFromJson(it)
            }
        }
    }

    private suspend fun getCategories(): List<EventCategory> {
        return withContext(Dispatchers.IO) {
            delay(TIMEOUT)
            getApplication<Application>().assets.open("categories.json").use {
                jsonHelper.getCategoryFromJson(it)
            }
        }
    }

    private fun fetchData() {
        val coroutineException = CoroutineExceptionHandler.create(EXCEPTION_TAG) {
            _eventList.value = emptyList()
            initList = emptyList()
            progress.value = false
            unreadCount = 0
            fetchUnreadCount(0)
            filterCategories = emptyList()
        }
        viewModelScope.launch(coroutineException) {
            val deferreds = listOf(
                async {
                    val eventList = getEvents()
                    _eventList.value = eventList
                    initList = eventList
                    unreadCount = eventList.size
                    fetchUnreadCount(eventList.size)
                },
                async {
                    val categoryList = getCategories()
                    filterCategories = categoryList.map { it.id }
                }
            )
            deferreds.awaitAll()
            progress.value = false
        }
    }

    fun onCategoriesChanged(categories: List<Int>) {
        filterCategories = categories
        filteredList()
    }

    private fun filteredList() {
        _eventList.value = initList.filter { event ->
            filterCategories.any {
                event.categoryIds.contains(it)
            }
        }
    }

    fun readEvent(event: Event) {
        if (!readEvents.contains(event.id)) {
            unreadCount--
            readEvents.add(event.id)
            fetchUnreadCount(unreadCount)
        }
    }

    private fun fetchUnreadCount(unreadCount: Int) {
        EventFlow.publish(unreadCount)
    }

    companion object {
        private const val TIMEOUT = 5000L
        private const val EXCEPTION_TAG = "NewsViewModel"
    }
}

