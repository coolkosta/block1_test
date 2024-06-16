package com.coolkosta.simbirsofttestapp.api

import kotlinx.coroutines.flow.flow


class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override fun getCategories() = flow {
        emit(apiService.getCategories())
    }

    override fun getEvents() = flow {
        emit(apiService.getEvents())
    }
}