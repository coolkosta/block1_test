package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.entity.Category
import com.coolkosta.simbirsofttestapp.entity.CategoryEntity

object CategoryMapper {
    fun fromCategoryToEventCategory(category: Category): CategoryEntity {
        return CategoryEntity(
            id = category.id.toInt(),
            title = category.name
        )
    }
}