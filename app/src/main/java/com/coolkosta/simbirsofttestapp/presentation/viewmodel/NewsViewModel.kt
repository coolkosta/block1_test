package com.coolkosta.simbirsofttestapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.domain.interactor.CategoryInteractor
import com.coolkosta.simbirsofttestapp.domain.interactor.EventInteractor
import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.model.Event
import com.coolkosta.simbirsofttestapp.util.EventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsViewModel @Inject constructor(
    private val eventInteractor: EventInteractor,
    private val categoryInteractor: CategoryInteractor,
    @Named("IO") private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    val progress = MutableLiveData(true)

    var filterCategories: List<Int> = listOf()
    private var initList: List<Event> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    init {
        getData()
    }

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
                    val list = withContext(dispatcherIO) {
                        runCatching {
                            eventInteractor.getEventList()
                        }.getOrElse { ex ->
                            Log.e(NEWS_VM_EXCEPTION_TAG, "Can't get event data: ${ex.message} ")
                            emptyList()
                        }
                    }
                    updateEventList(list)
                },
                async {
                    val list = withContext(dispatcherIO) {
                        runCatching {
                            categoryInteractor.getCategoryList()
                        }.getOrElse { ex ->
                            Log.e(NEWS_VM_EXCEPTION_TAG, "Can't get category data: ${ex.message} ")
                            emptyList()
                        }
                    }
                    categoryInteractor.getCategoryList()
                    updateCategoryList(list)
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
        private const val NEWS_VM_EXCEPTION_TAG = "NewsViewModel"
    }
}

