package com.coolkosta.simbirsofttestapp.api

import com.coolkosta.simbirsofttestapp.entity.Category
import com.coolkosta.simbirsofttestapp.entity.Event
import retrofit2.http.GET

interface ApiService {
    @GET("categories.json")
    suspend fun getCategories(): Map<String, Category>

    @GET("events.json")
    suspend fun getEvents(): List<Event>
}
