package com.coolkosta.simbirsofttestapp.domain.repository

import com.coolkosta.simbirsofttestapp.domain.model.CategoryEntity

fun interface CategoryRepository {
    suspend fun getCategories(): List<CategoryEntity>
}