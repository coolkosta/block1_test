package com.coolkosta.simbirsofttestapp.data.source.remote.api

import com.coolkosta.simbirsofttestapp.data.source.remote.dto.CategoryDto
import com.coolkosta.simbirsofttestapp.data.source.remote.dto.EventDto
import retrofit2.http.GET

interface ApiService {
    @GET("categories.json")
    suspend fun getCategories(): Map<String, CategoryDto>

    @GET("events.json")
    suspend fun getEvents(): List<EventDto>
}
