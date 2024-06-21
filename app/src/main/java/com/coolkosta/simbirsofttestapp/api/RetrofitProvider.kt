package com.coolkosta.simbirsofttestapp.api

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private val loginInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loginInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://simbirsoft-test-app-default-rtdb.europe-west1.firebasedatabase.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()

    val categoriesApi: CategoriesApi = retrofit.create(CategoriesApi::class.java)
    val eventsApi: EventsApi = retrofit.create(EventsApi::class.java)
}
