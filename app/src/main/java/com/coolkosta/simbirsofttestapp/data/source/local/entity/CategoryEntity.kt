package com.coolkosta.simbirsofttestapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coolkosta.simbirsofttestapp.domain.model.Category

@Entity(tableName = "category_entity")
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val title: String
) {
    fun toCategory(): Category {
        return Category(
            id = id,
            title = title
        )
    }
}
