package com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.domain.interactor.CategoryInteractor
import com.coolkosta.simbirsofttestapp.domain.interactor.EventInteractor
import com.coolkosta.simbirsofttestapp.domain.model.CategoryEntity
import com.coolkosta.simbirsofttestapp.domain.model.EventEntity
import com.coolkosta.simbirsofttestapp.util.EventFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val eventInteractor: EventInteractor,
    private val categoryInteractor: CategoryInteractor,
) : ViewModel() {

    private val _state = MutableLiveData<NewsState>()
    val state: LiveData<NewsState> get() = _state

    private val _sideEffect = MutableLiveData<NewsSideEffect>()
    val sideEffect: LiveData<NewsSideEffect> get() = _sideEffect


    private var filterCategories: List<Int> = listOf()
    private var initList: List<EventEntity> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    init {
        getData()
    }

    private fun updateNewsState(
        eventEntities: List<EventEntity>,
        categories: List<CategoryEntity>
    ) {
        filterCategories = categories.map { it.id }
        _state.value = NewsState.Success(eventEntities, filterCategories)
        initList = eventEntities
        unreadCount = eventEntities.size
        fetchUnreadCount(eventEntities.size)
    }

    private fun getData() {
        viewModelScope.launch {
            _state.value = NewsState.Loading
            runCatching {
                val eventsDeferred =
                    async {
                        eventInteractor.getEventList()
                    }

                val categoriesDeferred =
                    async {
                        categoryInteractor.getCategoryList()
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
            is NewsEvent.EventsFiltered -> {
                onCategoriesChanged(newsEvent.filterList)
            }

            is NewsEvent.EventReaded -> {
                readEvent(newsEvent.eventEntity)
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

    private fun readEvent(eventEntity: EventEntity) {
        if (!readEvents.contains(eventEntity.id)) {
            unreadCount--
            readEvents.add(eventEntity.id)
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

