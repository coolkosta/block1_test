package com.coolkosta.news.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coolkosta.news.data.source.local.dao.CategoryDao
import com.coolkosta.news.data.source.local.model.CategoryDbModel

@Database(entities = [CategoryDbModel::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}
