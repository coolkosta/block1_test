package com.coolkosta.simbirsofttestapp.api

import com.coolkosta.simbirsofttestapp.entity.Category
import com.coolkosta.simbirsofttestapp.entity.RemoteEvent
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

fun interface CategoriesApi {
    @GET("categories.json")
    fun getCategories(): Single<Map<String, Category>>
}

fun interface EventsApi {
    @GET("events.json")
    fun getEvents(): Single<List<RemoteEvent>>
}
