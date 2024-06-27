package com.coolkosta.simbirsofttestapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coolkosta.simbirsofttestapp.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
