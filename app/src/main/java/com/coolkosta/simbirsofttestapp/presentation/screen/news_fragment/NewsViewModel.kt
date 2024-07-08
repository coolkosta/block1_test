package com.coolkosta.simbirsofttestapp.presentation.screen.news_fragment

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

    private val _sideEffect = MutableLiveData<NewsSideEffect>()
    val sideEffect: LiveData<NewsSideEffect> get() = _sideEffect


    private var filterCategories: List<Int> = listOf()
    private var initList: List<Event> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    init {
        getData()
    }

    private fun updateNewsState(events: List<Event>, categories: List<Category>) {
        filterCategories = categories.map { it.id }
        _state.value = NewsState.Success(events, filterCategories)
        initList = events
        unreadCount = events.size
        fetchUnreadCount(events.size)
    }

    private fun getData() {
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
                updateNewsState(events, categories)
            }.onFailure { ex ->
                Log.e(NEWS_VM_EXCEPTION_TAG, "Can't get data: ${ex.message}")
                _state.value = NewsState.Error(ex)
                _sideEffect.value = NewsSideEffect.ShowErrorToast(ex.message ?: "Unknown error")
            }
        }
    }

    fun sendEvent(newsEvent: NewsEvent) {
        when (newsEvent) {
            is NewsEvent.FilteredEvents -> {
                onCategoriesChanged(newsEvent.filterList)
            }

            is NewsEvent.EventReader -> {
                readEvent(newsEvent.event)
            }
        }
    }

    private fun onCategoriesChanged(categories: List<Int>) {
        filterCategories = categories
        filteredList()
    }

    private fun filteredList() {
        val filteredList = initList.filter { event ->
            filterCategories.any {
                event.categoryIds.contains(it)
            }
        }
        _state.value = NewsState.Success(filteredList, filterCategories)
    }

    private fun readEvent(event: Event) {
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

