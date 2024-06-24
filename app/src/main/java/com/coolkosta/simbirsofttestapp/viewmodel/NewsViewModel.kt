package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.api.RetrofitProvider
import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.entity.EventCategory
import com.coolkosta.simbirsofttestapp.util.CategoryMapper
import com.coolkosta.simbirsofttestapp.util.EventFlow
import com.coolkosta.simbirsofttestapp.util.EventMapper
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

    private suspend fun getLocalEvents(): List<Event> {
        return withContext(Dispatchers.IO) {
            delay(TIMEOUT)
            getApplication<Application>().assets.open("events.json").use {
                jsonHelper.getEventsFromJson(it)
            }
        }
    }

    private suspend fun getLocalCategories(): List<EventCategory> {
        return withContext(Dispatchers.IO) {
            delay(TIMEOUT)
            getApplication<Application>().assets.open("categories.json").use {
                jsonHelper.getCategoryFromJson(it)
            }
        }
    }

    private fun updateEventList(eventList: List<Event>) {
        _eventList.value = eventList
        initList = eventList
        unreadCount = eventList.size
        fetchUnreadCount(eventList.size)
    }

    private fun updateCategoryList(categoryList: List<EventCategory>) {
        filterCategories = categoryList.map { it.id }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val deferrds = listOf(
                async {
                    val eventList = withContext(Dispatchers.IO) {
                        runCatching {
                            RetrofitProvider.apiService.getEvents().map {
                                EventMapper.fromRemoteEventToEvent(it)
                            }
                        }.getOrElse {
                            getLocalEvents()
                        }
                    }
                    updateEventList(eventList)
                },
                async {
                    val categoryList = withContext(Dispatchers.IO) {
                        runCatching {
                            RetrofitProvider.apiService.getCategories().map {
                                CategoryMapper.fromCategoryToEventCategory(it.value)
                            }
                        }.getOrElse { getLocalCategories() }
                    }
                    updateCategoryList(categoryList)
                }
            )
            deferrds.awaitAll()
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
    }
}

