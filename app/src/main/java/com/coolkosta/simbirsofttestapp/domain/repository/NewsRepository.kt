package com.coolkosta.simbirsofttestapp.domain.repository

import com.coolkosta.simbirsofttestapp.data.source.remote.Resource
import com.coolkosta.simbirsofttestapp.data.source.remote.dto.CategoryDto
import com.coolkosta.simbirsofttestapp.data.source.remote.dto.EventDto
import com.coolkosta.simbirsofttestapp.domain.model.Category
import com.coolkosta.simbirsofttestapp.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getEvents(): Flow<Resource<List<Event>>>
    fun getCategories(): Flow<Resource<List<Category>>>
}