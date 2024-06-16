package com.coolkosta.simbirsofttestapp.api

import com.coolkosta.simbirsofttestapp.entity.Category
import com.coolkosta.simbirsofttestapp.entity.RemoteEvent
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    fun getCategories(): Flow<Map<String, Category>>
    fun getEvents(): Flow<List<RemoteEvent>>
}
