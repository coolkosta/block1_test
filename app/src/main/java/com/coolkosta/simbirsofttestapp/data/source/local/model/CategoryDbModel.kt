package com.coolkosta.simbirsofttestapp.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_entity")
data class CategoryDbModel(
    @PrimaryKey
    val id: Int,
    val title: String
)
