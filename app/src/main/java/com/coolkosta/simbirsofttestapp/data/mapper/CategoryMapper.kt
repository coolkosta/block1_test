package com.coolkosta.simbirsofttestapp.data.mapper

import com.coolkosta.simbirsofttestapp.data.source.local.model.CategoryDbModel
import com.coolkosta.simbirsofttestapp.data.source.remote.model.CategoryResponse
import com.coolkosta.simbirsofttestapp.domain.model.CategoryEntity

object CategoryMapper {
    fun fromCategoryResponseToCategoryDbModel(categoryResponse: CategoryResponse): CategoryDbModel {
        return CategoryDbModel(
            id = categoryResponse.id.toInt(),
            title = categoryResponse.name
        )
    }

    fun fromCategoryDbModelToCategory(categoryDbModel: CategoryDbModel): CategoryEntity {
        return CategoryEntity(
            id = categoryDbModel.id,
            title = categoryDbModel.title
        )
    }
}