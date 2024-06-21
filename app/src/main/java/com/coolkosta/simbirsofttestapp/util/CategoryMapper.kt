package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.entity.Category
import com.coolkosta.simbirsofttestapp.entity.EventCategory

object CategoryMapper {
    fun fromCategoryToEventCategory(category: Category): EventCategory {
        return EventCategory(
            id = category.id.toInt(),
            title = category.name
        )
    }
}