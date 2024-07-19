package com.coolkosta.news.domain.interactor


import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.domain.repository.EventRepository
import javax.inject.Inject

class EventInteractor @Inject constructor(
    private val repository: EventRepository
) {
    suspend fun getEventList(): List<EventEntity> {
        return repository.getEvents()
    }
}