package com.coolkosta.simbirsofttestapp.data.repository

import android.util.Log
import com.coolkosta.simbirsofttestapp.data.mapper.EventMapper
import com.coolkosta.simbirsofttestapp.data.source.local.dao.EventDao
import com.coolkosta.simbirsofttestapp.data.source.remote.api.ApiService
import com.coolkosta.simbirsofttestapp.domain.model.EventEntity
import com.coolkosta.simbirsofttestapp.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val eventDao: EventDao,
    private val dispatcherIO: CoroutineDispatcher
) : EventRepository {
    override suspend fun getEvents(): List<EventEntity> {
        val eventList = withContext(dispatcherIO) {
            runCatching {
                fetchAndInsertEvents()
            }.getOrElse { ex ->
                Log.e(
                    EventRepositoryImpl::class.simpleName,
                    "Can't get remote event data: ${ex.message}"
                )
                getEventsFromDb()
            }
        }
        return eventList
    }

    private suspend fun fetchAndInsertEvents(
    ): List<EventEntity> {
        val eventDbList = api.getEvents().map { EventMapper.fromEventResponseToEventDbModel(it) }
        eventDao.insertEvent(eventDbList)
        return eventDbList.map { EventMapper.fromEventDbModelToEvent(it) }
    }

    private suspend fun getEventsFromDb(): List<EventEntity> {
        val eventList = eventDao.getAllData().map { EventMapper.fromEventDbModelToEvent(it) }
        check(eventList.isNotEmpty())
        return eventList
    }
}