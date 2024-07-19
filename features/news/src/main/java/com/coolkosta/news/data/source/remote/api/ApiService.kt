package com.coolkosta.news.data.source.remote.api

import com.coolkosta.news.data.source.remote.model.CategoryResponse
import com.coolkosta.news.data.source.remote.model.EventResponse
import retrofit2.http.GET

interface ApiService {
    @GET("categories.json")
    suspend fun getCategories(): Map<String, CategoryResponse>

    @GET("events.json")
    suspend fun getEvents(): List<EventResponse>
}
