package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.data.source.local.entity.CategoryEntity
import com.coolkosta.simbirsofttestapp.data.source.remote.dto.CategoryDto


object CategoryMapper {
    fun fromCategoryToEventCategory(categoryDto: CategoryDto): CategoryEntity {
        return CategoryEntity(
            id = categoryDto.id.toInt(),
            title = categoryDto.name
        )
    }
}