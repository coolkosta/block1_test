package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.coolkosta.simbirsofttestapp.api.RetrofitProvider
import com.coolkosta.simbirsofttestapp.db.CategoryDatabase
import com.coolkosta.simbirsofttestapp.db.EventDatabase
import com.coolkosta.simbirsofttestapp.entity.CategoryEntity
import com.coolkosta.simbirsofttestapp.entity.EventEntity
import com.coolkosta.simbirsofttestapp.util.CategoryMapper
import com.coolkosta.simbirsofttestapp.util.EventFlow
import com.coolkosta.simbirsofttestapp.util.EventMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private var _eventList = MutableLiveData<List<EventEntity>>()
    val eventList: LiveData<List<EventEntity>> get() = _eventList

    val progress = MutableLiveData(true)

    var filterCategories: List<Int> = listOf()
    private var initList: List<EventEntity> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    private val eventDb = Room.databaseBuilder(
        application,
        EventDatabase::class.java,
        "event_database"
    ).build()

    private val categoryDb = Room.databaseBuilder(
        application,
        CategoryDatabase::class.java,
        "category_database"
    ).build()

    private val eventDao = eventDb.eventDao()

    private val categoryDao = categoryDb.categoryDao()

    init {
        fetchData()
    }

    private fun updateEventList(eventList: List<EventEntity>) {
        _eventList.value = eventList
        initList = eventList
        unreadCount = eventList.size
        fetchUnreadCount(eventList.size)
    }

    private fun updateCategoryList(categoryList: List<CategoryEntity>) {
        filterCategories = categoryList.map { it.id }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val deferrds = listOf(
                async {
                    val eventEntityList = withContext(Dispatchers.IO) {
                        runCatching {
                            val eventList = RetrofitProvider.apiService.getEvents().map {
                                EventMapper.fromRemoteEventToEvent(it)
                            }
                            eventDao.insertEvent(eventList)

                        }.onFailure {
                            Log.e(EXCEPTION_TAG, "Error loading events: ${it.message}")
                        }
                        eventDao.getAllData()
                    }
                    updateEventList(eventEntityList)
                },
                async {
                    val categoryEntityList = withContext(Dispatchers.IO) {
                        runCatching {
                            val categoryList = RetrofitProvider.apiService.getCategories().map {
                                CategoryMapper.fromCategoryToEventCategory(it.value)
                            }
                            categoryDao.insertEventCategory(categoryList)
                        }.onFailure {
                            Log.e(EXCEPTION_TAG, "Error loading category: ${it.message}")
                        }
                        categoryDao.getAllCategories()
                    }
                    updateCategoryList(categoryEntityList)
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

    fun readEvent(event: EventEntity) {
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
        private const val EXCEPTION_TAG = "NewsViewModel"
    }
}

