package com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment

import com.coolkosta.simbirsofttestapp.domain.model.CategoryEntity

data class NewsFilterState(
    val categories: List<CategoryEntity> = emptyList(),
    val filteredList: MutableList<Int> = mutableListOf()
)
