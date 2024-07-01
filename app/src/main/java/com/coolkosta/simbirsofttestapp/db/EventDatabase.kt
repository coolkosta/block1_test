package com.coolkosta.simbirsofttestapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coolkosta.simbirsofttestapp.entity.CategoryIdsConverter
import com.coolkosta.simbirsofttestapp.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1)
@TypeConverters(CategoryIdsConverter::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}