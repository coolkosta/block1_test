package com.coolkosta.simbirsofttestapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolkosta.simbirsofttestapp.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventCategory(category: CategoryEntity)

    @Query("SELECT * FROM category_entity")
    fun getAllCategories(): List<CategoryEntity>
}
