package com.coolkosta.simbirsofttestapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_entity")
data class CategoryEntity(
   @PrimaryKey
    val id: Int,
    val title: String
)
