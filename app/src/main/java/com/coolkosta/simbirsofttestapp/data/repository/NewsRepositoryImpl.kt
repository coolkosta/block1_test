package com.coolkosta.simbirsofttestapp.data.repository

import com.coolkosta.simbirsofttestapp.data.source.local.dao.CategoryDao
import com.coolkosta.simbirsofttestapp.data.source.local.dao.EventDao
import com.coolkosta.simbirsofttestapp.data.source.remote.Resource
import com.coolkosta.simbirsofttestapp.data.source.remote.api.ApiService
import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.model.Event
import com.coolkosta.simbirsofttestapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val eventDao: EventDao,
    private val categoryDao: CategoryDao
) : NewsRepository {

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        try {
            fetchAndInsertEvents(api, eventDao)
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
        emit(Resource.Success(getEventsFromDb(eventDao)))
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            fetchAndInsertCategories(api, categoryDao)
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
        emit(Resource.Success(getCategoriesFromDb(categoryDao)))
    }

    private suspend fun fetchAndInsertEvents(
        api: ApiService, eventDao: EventDao,
    ) {
        val eventEntityList = api.getEvents().map { it.toEventEntity() }
        eventDao.insertEvent(eventEntityList)
    }

    private suspend fun getEventsFromDb(eventDao: EventDao): List<Event> {
        return eventDao.getAllData().map { it.toEvent() }
    }

    private suspend fun fetchAndInsertCategories(
        api: ApiService,
        categoryDao: CategoryDao
    ) {
        val categoryEntityList = api.getCategories().map { it.value.toCategoryEntity() }
        categoryDao.insertEventCategory(categoryEntityList)
    }

    private suspend fun getCategoriesFromDb(categoryDao: CategoryDao): List<Category> {
        return categoryDao.getAllCategories().map { it.toCategory() }
    }

}