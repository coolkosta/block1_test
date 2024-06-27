package com.coolkosta.simbirsofttestapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coolkosta.simbirsofttestapp.entity.EventEntity

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Query("SELECT * FROM event_entity")
    fun getAllData(): List<EventEntity>
}