package com.coolkosta.simbirsofttestapp.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coolkosta.simbirsofttestapp.data.source.local.dao.EventDao
import com.coolkosta.simbirsofttestapp.data.source.local.entity.CategoryIdsConverter
import com.coolkosta.simbirsofttestapp.data.source.local.entity.EventEntity


@Database(entities = [EventEntity::class], version = 1)
@TypeConverters(CategoryIdsConverter::class)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getDatabase(context: Context): EventDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "event_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}