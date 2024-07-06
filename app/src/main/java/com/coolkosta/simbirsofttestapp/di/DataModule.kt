package com.coolkosta.simbirsofttestapp.di

import android.content.Context
import androidx.room.Room
import com.coolkosta.simbirsofttestapp.common.Constants
import com.coolkosta.simbirsofttestapp.data.repository.CategoryRepositoryImpl
import com.coolkosta.simbirsofttestapp.data.repository.EventRepositoryImpl
import com.coolkosta.simbirsofttestapp.data.source.local.dao.CategoryDao
import com.coolkosta.simbirsofttestapp.data.source.local.dao.EventDao
import com.coolkosta.simbirsofttestapp.data.source.local.db.CategoryDatabase
import com.coolkosta.simbirsofttestapp.data.source.local.db.EventDatabase
import com.coolkosta.simbirsofttestapp.data.source.remote.api.ApiService
import com.coolkosta.simbirsofttestapp.domain.repository.CategoryRepository
import com.coolkosta.simbirsofttestapp.domain.repository.EventRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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
    fun provideEventDatabase(context: Context): EventDatabase {
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            Constants.EVENT_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideEventDao(database: EventDatabase) = database.eventDao()

    @Singleton
    @Provides
    fun provideCategoryDatabase(context: Context): CategoryDatabase {
        return Room.databaseBuilder(
            context,
            CategoryDatabase::class.java,
            Constants.CATEGORY_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun categoryDao(database: CategoryDatabase) = database.categoryDao()

    @Singleton
    @Provides
    fun provideEventRepository(
        apiService: ApiService,
        eventDao: EventDao,
        @Named("IO") dispatcher: CoroutineDispatcher
    ): EventRepository {
        return EventRepositoryImpl(apiService, eventDao, dispatcher)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        apiService: ApiService,
        categoryDao: CategoryDao,
        @Named("IO") dispatcher: CoroutineDispatcher
    ): CategoryRepository {
        return CategoryRepositoryImpl(apiService, categoryDao, dispatcher)
    }
}