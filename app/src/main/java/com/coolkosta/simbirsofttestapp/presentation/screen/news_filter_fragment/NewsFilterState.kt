package com.coolkosta.simbirsofttestapp.presentation.screen.news_filter_fragment

import com.coolkosta.simbirsofttestapp.domain.model.Category

data class NewsFilterState(
    val categories: List<Category> = emptyList(),
    val filteredList: MutableList<Int> = mutableListOf()
)
