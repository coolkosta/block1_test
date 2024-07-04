package com.coolkosta.simbirsofttestapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolkosta.simbirsofttestapp.data.source.local.entity.EventEntity


@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(eventList: List<EventEntity>)

    @Query("SELECT * FROM event_entity")
    suspend fun getAllData(): List<EventEntity>
}