package com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolkosta.simbirsofttestapp.domain.interactor.CategoryInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFilterViewModel @Inject constructor(
    private val categoryInteractor: CategoryInteractor,
) : ViewModel() {

    private val _state = MutableStateFlow(NewsFilterState())
    val state = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<NewsFilterSideEffect>() }
    val sideEffect: Flow<NewsFilterSideEffect> by lazy { _sideEffect.receiveAsFlow() }

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            runCatching {
                val categories = categoryInteractor.getCategoryList()
                _state.update { state -> state.copy(categories = categories) }
            }.onFailure { ex ->
                Log.e(NewsFilterViewModel::class.simpleName, "Can't get data: ${ex.message}")
                this.launch {
                    _sideEffect.send(
                        NewsFilterSideEffect.ShowErrorToast(
                            ex.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }

    fun sendEvent(newsFilterEvent: NewsFilterEvent) {
        when (newsFilterEvent) {
            is NewsFilterEvent.FilterCategorySelected -> initFilterCategories(newsFilterEvent.categoriesIds)
            is NewsFilterEvent.SwitchChanged -> onSwitchChanged(
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