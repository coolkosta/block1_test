package com.coolkosta.simbirsofttestapp.domain.interactor


import com.coolkosta.simbirsofttestapp.data.source.remote.Resource
import com.coolkosta.simbirsofttestapp.domain.model.Event
import com.coolkosta.simbirsofttestapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsInteractor @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<Event>>> {
        return repository.getEvents()
    }
}