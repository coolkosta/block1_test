package com.coolkosta.simbirsofttestapp.presentation.screen.news_filter_fragment

sealed class NewsFilterEvent {
    data class FilterCategory(val categoriesIds: List<Int>): NewsFilterEvent()
    data class SwitchChanger(val idCategory: Int, val isSwitchEnable: Boolean): NewsFilterEvent()
}