package com.coolkosta.simbirsofttestapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolkosta.simbirsofttestapp.data.source.local.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventCategory(categoryList: List<CategoryEntity>)

    @Query("SELECT * FROM category_entity")
    suspend fun getAllCategories(): List<CategoryEntity>
}
