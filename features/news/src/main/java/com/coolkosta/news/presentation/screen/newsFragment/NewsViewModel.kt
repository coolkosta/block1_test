package com.coolkosta.news.presentation.screen.newsFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.news.domain.interactor.CategoryInteractor
import com.coolkosta.news.domain.interactor.EventInteractor
import com.coolkosta.news.domain.model.CategoryEntity
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.simbirsofttestapp.util.CoroutineExceptionHandler
import com.coolkosta.simbirsofttestapp.util.EventFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val eventInteractor: EventInteractor,
    private val categoryInteractor: CategoryInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow<NewsState>(NewsState.Loading)
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<NewsSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

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
        _state.update { NewsState.Success(eventEntities, filterCategories) }
        initList = eventEntities
        unreadCount = eventEntities.size
        fetchUnreadCount(eventEntities.size)
    }

    private fun getData() {
        val coroutineExceptionHandler =
            CoroutineExceptionHandler.create(NEWS_VM_EXCEPTION_TAG) { ex ->
                _state.update { NewsState.Error }
                _sideEffect.trySend(NewsSideEffect.ShowErrorToast(ex))
            }
        viewModelScope.launch(coroutineExceptionHandler) {
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

