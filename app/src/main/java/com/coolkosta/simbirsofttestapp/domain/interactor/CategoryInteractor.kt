package com.coolkosta.simbirsofttestapp.domain.interactor

import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryInteractor @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun getCategoryList(): List<Category> {
        return repository.getCategories()
    }
}