package com.coolkosta.simbirsofttestapp.domain.interactor

import com.coolkosta.simbirsofttestapp.data.source.remote.Resource
import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesInteractor @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> {
        return repository.getCategories()
    }
}