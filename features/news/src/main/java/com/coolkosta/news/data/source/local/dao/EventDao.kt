package com.coolkosta.news.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolkosta.news.data.source.local.model.EventDbModel


@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventList: List<EventDbModel>)

    @Query("SELECT * FROM event_entity")
    suspend fun getAllData(): List<EventDbModel>
}