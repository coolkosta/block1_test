package com.coolkosta.simbirsofttestapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolkosta.simbirsofttestapp.data.source.local.model.CategoryDbModel

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventCategory(categoryList: List<CategoryDbModel>)

    @Query("SELECT * FROM category_entity")
    suspend fun getAllCategories(): List<CategoryDbModel>
}
