package com.coolkosta.simbirsofttestapp.data.repository

import android.util.Log
import com.coolkosta.simbirsofttestapp.data.source.local.dao.CategoryDao
import com.coolkosta.simbirsofttestapp.data.source.remote.api.ApiService
import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.repository.CategoryRepository
import com.coolkosta.simbirsofttestapp.data.mapper.CategoryMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val categoryDao: CategoryDao,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        val categoryList = withContext(dispatcher) {
            runCatching {
                fetchCategoryAndInsert(api, categoryDao)
            }.getOrElse { ex ->
                Log.e(CATEGORY_REP_EXCEPTION_TAG, "Can't get remote category data: ${ex.message}")
                getCategoriesFromDb(categoryDao)
            }
        }
        return categoryList
    }

    private suspend fun fetchCategoryAndInsert(
        api: ApiService,
        categoryDao: CategoryDao
    ): List<Category> {
        val categoryDbList = api.getCategories()
            .map { CategoryMapper.fromCategoryResponseToCategoryDbModel(it.value) }
        categoryDao.insertEventCategory(categoryDbList)
        return categoryDbList.map { CategoryMapper.fromCategoryDbModelToCategory(it) }
    }

    private suspend fun getCategoriesFromDb(categoryDao: CategoryDao): List<Category> {
        return categoryDao.getAllCategories()
            .map { CategoryMapper.fromCategoryDbModelToCategory(it) }
    }

    companion object {
        private const val CATEGORY_REP_EXCEPTION_TAG = "CategoryRepository"
    }

}