package com.coolkosta.news.data.mapper

import com.coolkosta.news.data.source.local.model.CategoryDbModel
import com.coolkosta.news.data.source.remote.model.CategoryResponse
import com.coolkosta.news.domain.model.CategoryEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryMapperTest {
    @Test
    fun testFromCategoryResponseToCategoryDbModel() {
        val categoryResponse = CategoryResponse(id = "1", nameEn = "Category Test Response", "Test Category", "image.jpg" )
        val expectedCategoryDbModel = CategoryDbModel(id = 1, title = "Test Category")

        val actualCategoryDbModel = CategoryMapper.fromCategoryResponseToCategoryDbModel(categoryResponse)

        assertEquals(expectedCategoryDbModel, actualCategoryDbModel)
    }

    @Test
    fun testFromCategoryDbModelToCategory() {
        val categoryDbModel = CategoryDbModel(id = 1, title = "Test Category")
        val expectedCategoryEntity = CategoryEntity(id = 1, title = "Test Category")

        val actualCategoryEntity = CategoryMapper.fromCategoryDbModelToCategory(categoryDbModel)

        assertEquals(expectedCategoryEntity, actualCategoryEntity)
    }
}