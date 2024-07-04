package com.coolkosta.simbirsofttestapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.data.source.remote.Resource
import com.coolkosta.simbirsofttestapp.domain.interactor.GetCategoriesInteractor
import com.coolkosta.simbirsofttestapp.domain.interactor.GetEventsInteractor
import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.model.Event
import com.coolkosta.simbirsofttestapp.util.EventFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    application: Application,
    private val getEventsInteractor: GetEventsInteractor,
    private val getCategoriesInteractor: GetCategoriesInteractor
) : AndroidViewModel(application) {

    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    val progress = MutableLiveData(true)

    var filterCategories: List<Int> = listOf()
    private var initList: List<Event> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    private fun updateEventList(eventList: List<Event>) {
        _eventList.value = eventList
        initList = eventList
        unreadCount = eventList.size
        fetchUnreadCount(eventList.size)
    }

    private fun updateCategoryList(categoryList: List<Category>) {
        filterCategories = categoryList.map { it.id }
    }

    private fun getData() {
        viewModelScope.launch {
            val deferreds = listOf(
                async {
                    withContext(Dispatchers.IO) {
                        getEventsInteractor.invoke().onEach { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    updateEventList(result.data ?: emptyList())
                                }

                                is Resource.Error -> {
                                    Log.d(EXCEPTION_TAG, "Test event")
                                }

                                is Resource.Success -> {
                                    updateEventList(result.data ?: emptyList())
                                }
                            }
                        }
                    }
                },
                async {
                    withContext(Dispatchers.IO) {
                        getCategoriesInteractor.invoke().onEach { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    updateCategoryList(result.data ?: emptyList())
                                }

                                is Resource.Error -> {
                                    Log.d(EXCEPTION_TAG, "Test category")
                                }

                                is Resource.Success -> {
                                    updateCategoryList(result.data ?: emptyList())
                                }
                            }
                        }
                    }
                }
            )
            deferreds.awaitAll()
            progress.value = false
        }
    }


    /*private fun fetchData() {
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
    }*/

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
        private const val EXCEPTION_TAG = "NewsViewModel"
    }
}

