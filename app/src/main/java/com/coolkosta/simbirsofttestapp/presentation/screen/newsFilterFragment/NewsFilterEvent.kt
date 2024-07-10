package com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment

sealed interface NewsFilterEvent {
    data class FilterCategorySelected(val categoriesIds: List<Int>) : NewsFilterEvent
    data class SwitchChanged(val idCategory: Int, val isSwitchEnable: Boolean) : NewsFilterEvent
}