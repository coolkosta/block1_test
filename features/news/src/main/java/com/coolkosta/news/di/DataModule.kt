package com.coolkosta.news.di

import android.content.Context
import androidx.room.Room
import com.coolkosta.news.common.Constants.BASE_URL
import com.coolkosta.news.common.Constants.CATEGORY_DATABASE_NAME
import com.coolkosta.news.common.Constants.EVENT_DATABASE_NAME
import com.coolkosta.news.data.repository.CategoryRepositoryImpl
import com.coolkosta.news.data.repository.EventRepositoryImpl
import com.coolkosta.news.data.source.local.dao.CategoryDao
import com.coolkosta.news.data.source.local.dao.EventDao
import com.coolkosta.news.data.source.local.db.CategoryDatabase
import com.coolkosta.news.data.source.local.db.EventDatabase
import com.coolkosta.news.data.source.remote.api.ApiService
import com.coolkosta.news.domain.repository.CategoryRepository
import com.coolkosta.news.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideEventDatabase(context: Context): EventDatabase {
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            EVENT_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideEventDao(database: EventDatabase) =
        database.eventDao()

    @Singleton
    @Provides
    fun provideCategoryDatabase(context: Context): CategoryDatabase {
        return Room.databaseBuilder(
            context,
            CategoryDatabase::class.java,
            CATEGORY_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun categoryDao(database: CategoryDatabase) =
        database.categoryDao()

    @Singleton
    @Provides
    fun provideEventRepository(
        apiService: ApiService,
        eventDao: EventDao,
        dispatcher: CoroutineDispatcher
    ): EventRepository {
        return EventRepositoryImpl(
            apiService,
            eventDao,
            dispatcher
        )
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        apiService: ApiService,
        categoryDao: CategoryDao,
        dispatcher: CoroutineDispatcher
    ): CategoryRepository {
        return CategoryRepositoryImpl(
            apiService,
            categoryDao,
            dispatcher
        )
    }
}