package com.coolkosta.news.domain.repository

import com.coolkosta.news.domain.model.CategoryEntity

fun interface CategoryRepository {
    suspend fun getCategories(): List<CategoryEntity>
}