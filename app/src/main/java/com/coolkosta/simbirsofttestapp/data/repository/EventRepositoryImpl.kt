package com.coolkosta.simbirsofttestapp.data.repository

import android.util.Log
import com.coolkosta.simbirsofttestapp.data.source.local.dao.EventDao
import com.coolkosta.simbirsofttestapp.data.source.remote.api.ApiService
import com.coolkosta.simbirsofttestapp.domain.model.Event
import com.coolkosta.simbirsofttestapp.domain.repository.EventRepository
import com.coolkosta.simbirsofttestapp.data.mapper.EventMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val eventDao: EventDao,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : EventRepository {
    override suspend fun getEvents(): List<Event> {
        val eventList = withContext(dispatcher) {
            runCatching {
                fetchAndInsertEvents(api, eventDao)
            }.getOrElse { ex ->
                Log.e(EVENT_REP_EXCEPTION_TAG, "Can't get remote event data: ${ex.message}")
                getEventsFromDb(eventDao)
            }
        }
        return eventList
    }


    private suspend fun fetchAndInsertEvents(
        api: ApiService, eventDao: EventDao,
    ): List<Event> {
        val eventDbList = api.getEvents().map { EventMapper.fromEventResponseToEventDbModel(it) }
        eventDao.insertEvent(eventDbList)
        return eventDbList.map { EventMapper.fromEventDbModelToEvent(it) }
    }

    private suspend fun getEventsFromDb(eventDao: EventDao): List<Event> {
        return eventDao.getAllData().map { EventMapper.fromEventDbModelToEvent(it) }
    }

    companion object {
        private const val EVENT_REP_EXCEPTION_TAG = "EventRepository"
    }
}