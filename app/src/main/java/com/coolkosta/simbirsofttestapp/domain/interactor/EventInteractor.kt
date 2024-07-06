package com.coolkosta.simbirsofttestapp.domain.interactor


import com.coolkosta.simbirsofttestapp.domain.model.Event
import com.coolkosta.simbirsofttestapp.domain.repository.EventRepository
import javax.inject.Inject

class EventInteractor @Inject constructor(
    private val repository: EventRepository
) {
    suspend fun getEventList(): List<Event> {
        return repository.getEvents()
    }
}