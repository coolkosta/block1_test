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
import com.coolkosta.simbirsofttestapp.presentation.NewsState
import com.coolkosta.simbirsofttestapp.util.EventFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsViewModel @Inject constructor(
    private val eventInteractor: EventInteractor,
    private val categoryInteractor: CategoryInteractor,
    @Named("IO") private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableLiveData<NewsState>()
    val state: LiveData<NewsState> get() = _state


    var filterCategories: List<Int> = listOf()
    private var initList: List<Event> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    init {
        getMviData()
    }

    private fun updateEventList(events: List<Event>) {
        _state.value = NewsState.Success(events)
        initList = events
        unreadCount = events.size
        fetchUnreadCount(events.size)
    }

    private fun updateCategoryList(categoryList: List<Category>) {
        filterCategories = categoryList.map { it.id }
    }

    private fun getMviData() {
        viewModelScope.launch {
            _state.value = NewsState.Loading
            runCatching {
                val eventsDeferred = withContext(dispatcherIO) {
                    async {
                        eventInteractor.getEventList()
                    }
                }
                val categoriesDeferred = withContext(dispatcherIO) {
                    async {
                        categoryInteractor.getCategoryList()
                    }
                }
                val events = eventsDeferred.await()
                val categories = categoriesDeferred.await()
                updateCategoryList(categories)
                updateEventList(events)
            }.onFailure { ex ->
                Log.e(NEWS_VM_EXCEPTION_TAG, "Can't get data: ${ex.message}")
            }
        }
    }

    fun onCategoriesChanged(categories: List<Int>) {
        filterCategories = categories
        filteredList()
    }

    private fun filteredList() {
        val filteredList = initList.filter { event ->
            filterCategories.any {
                event.categoryIds.contains(it)
            }
        }
        _state.value = NewsState.Success(filteredList)
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

