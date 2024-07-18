package com.coolkosta.news.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coolkosta.news.data.source.local.dao.EventDao
import com.coolkosta.news.data.source.local.model.CategoryIdsConverter
import com.coolkosta.news.data.source.local.model.EventDbModel

@Database(entities = [EventDbModel::class], version = 1)
@TypeConverters(CategoryIdsConverter::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}