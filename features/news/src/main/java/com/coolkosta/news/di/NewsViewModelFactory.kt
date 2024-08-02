package com.coolkosta.news.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailViewModel
import com.coolkosta.news.presentation.screen.newsFilterFragment.NewsFilterViewModel
import com.coolkosta.news.presentation.screen.newsFragment.NewsViewModel
import javax.inject.Inject
import javax.inject.Provider

class NewsViewModelFactory @Inject constructor(
    newsViewModelProvider: Provider<NewsViewModel>,
    newsFilterViewModelProvider: Provider<NewsFilterViewModel>,
    eventDetailViewModelProvider: Provider<EventDetailViewModel>
) : ViewModelProvider.Factory {
    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        NewsViewModel::class.java to newsViewModelProvider,
        NewsFilterViewModel::class.java to newsFilterViewModelProvider,
        EventDetailViewModel::class.java to eventDetailViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return providers[modelClass]!!.get() as T
    }
}