package com.coolkosta.news.domain.interactor

import com.coolkosta.news.domain.model.CategoryEntity
import com.coolkosta.news.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun getCategoryList(): List<CategoryEntity> {
        return repository.getCategories()
    }
}