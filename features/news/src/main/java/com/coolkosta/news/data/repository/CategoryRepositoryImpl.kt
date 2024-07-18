package com.coolkosta.news.data.repository

import android.util.Log
import com.coolkosta.news.data.mapper.CategoryMapper
import com.coolkosta.news.data.source.local.dao.CategoryDao
import com.coolkosta.news.data.source.remote.api.ApiService
import com.coolkosta.news.domain.model.CategoryEntity
import com.coolkosta.news.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val categoryDao: CategoryDao,
    private val dispatcherIO: CoroutineDispatcher
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryEntity> {
        val categoryList = withContext(dispatcherIO) {
            runCatching {
                fetchCategoryAndInsert()
            }.getOrElse { ex ->
                Log.e(
                    CategoryRepositoryImpl::class.simpleName,
                    "Can't get remote category data: ${ex.message}"
                )
                getCategoriesFromDb()
            }
        }
        check(categoryList.isNotEmpty())
        return categoryList
    }

    private suspend fun fetchCategoryAndInsert(): List<CategoryEntity> {
        val categoryDbList = api.getCategories()
            .map { CategoryMapper.fromCategoryResponseToCategoryDbModel(it.value) }
        categoryDao.insertEventCategory(categoryDbList)
        return categoryDbList.map { CategoryMapper.fromCategoryDbModelToCategory(it) }
    }

    private suspend fun getCategoriesFromDb(): List<CategoryEntity> {
        val categoryList = categoryDao.getAllCategories()
            .map { CategoryMapper.fromCategoryDbModelToCategory(it) }
        return categoryList
    }
}