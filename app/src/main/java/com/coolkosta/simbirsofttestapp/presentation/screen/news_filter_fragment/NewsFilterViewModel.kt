package com.coolkosta.simbirsofttestapp.presentation.screen.news_filter_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.domain.interactor.CategoryInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsFilterViewModel @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
    @Named("IO") private val dispatcherIO: CoroutineDispatcher
) : ViewModel() {

    private val _state = MutableStateFlow(NewsFilterState())
    val state = _state.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categories = withContext(dispatcherIO) {
                categoryInteractor.getCategoryList()
            }
            _state.update { state -> state.copy(categories = categories) }
        }
    }

    fun sendEvent(newsFilterEvent: NewsFilterEvent) {
        when (newsFilterEvent) {
            is NewsFilterEvent.FilterCategory -> initFilterCategories(newsFilterEvent.categoriesIds)
            is NewsFilterEvent.SwitchChanger -> onSwitchChanged(
                newsFilterEvent.idCategory, newsFilterEvent.isSwitchEnable
            )
        }
    }

    private fun initFilterCategories(categories: List<Int>) {
        _state.update { state -> state.copy(filteredList = categories.toMutableList()) }
    }

    private fun onSwitchChanged(idCategory: Int, isSwitchEnable: Boolean) {
        val currentList = _state.value.filteredList
        if (isSwitchEnable) {
            if (!_state.value.filteredList.contains(idCategory)) {
                currentList.add(idCategory)
                _state.update { state -> state.copy(filteredList = currentList) }
            }
        } else {
            currentList.remove(idCategory)
            _state.update { state -> state.copy(filteredList = currentList) }
        }
    }
}