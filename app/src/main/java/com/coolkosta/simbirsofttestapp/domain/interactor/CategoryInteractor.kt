package com.coolkosta.simbirsofttestapp.domain.interactor

import com.coolkosta.simbirsofttestapp.domain.model.CategoryEntity
import com.coolkosta.simbirsofttestapp.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun getCategoryList(): List<CategoryEntity> {
        return repository.getCategories()
    }
}