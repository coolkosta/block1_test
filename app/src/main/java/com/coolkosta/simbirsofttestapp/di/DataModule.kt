package com.coolkosta.simbirsofttestapp.di

import android.content.Context
import com.coolkosta.simbirsofttestapp.common.Constants
import com.coolkosta.simbirsofttestapp.data.repository.NewsRepositoryImpl
import com.coolkosta.simbirsofttestapp.data.source.local.dao.CategoryDao
import com.coolkosta.simbirsofttestapp.data.source.local.dao.EventDao
import com.coolkosta.simbirsofttestapp.data.source.local.db.CategoryDatabase
import com.coolkosta.simbirsofttestapp.data.source.local.db.EventDatabase
import com.coolkosta.simbirsofttestapp.data.source.remote.api.ApiService
import com.coolkosta.simbirsofttestapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DataModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loginInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideEventDatabase(context: Context) = EventDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideEventDao(database: EventDatabase) = database.eventDao()

    @Singleton
    @Provides
    fun provideCategoryDatabase(context: Context) =
        CategoryDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun categoryDao(database: CategoryDatabase) = database.categoryDao()

    @Singleton
    @Provides
    fun provideEventRepository(
        apiService: ApiService,
        eventDao: EventDao,
        categoryDao: CategoryDao
    ): NewsRepository {
        return NewsRepositoryImpl(apiService, eventDao, categoryDao)
    }
}