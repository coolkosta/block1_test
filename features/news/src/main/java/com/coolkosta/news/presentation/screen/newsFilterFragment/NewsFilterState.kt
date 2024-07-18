package com.coolkosta.news.presentation.screen.newsFilterFragment

import com.coolkosta.news.domain.model.CategoryEntity


data class NewsFilterState(
    val categories: List<CategoryEntity> = emptyList(),
    val filteredList: MutableList<Int> = mutableListOf()
)
