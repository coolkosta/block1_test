package com.coolkosta.simbirsofttestapp.api

import com.coolkosta.simbirsofttestapp.entity.Category
import com.coolkosta.simbirsofttestapp.entity.RemoteEvent
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

fun interface CategoriesApi {
    @GET("categories.json")
    fun getCategories(): Observable<Map<String, Category>>
}

fun interface EventsApi {
    @GET("events.json")
    fun getEvents(): Observable<List<RemoteEvent>>
}
