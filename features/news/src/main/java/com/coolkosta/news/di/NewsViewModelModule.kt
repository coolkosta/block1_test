package com.coolkosta.news.di

import androidx.lifecycle.ViewModelProvider
import com.coolkosta.news.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class NewsViewModelModule {
    @Provides
    fun provideViewModelFactory(viewModelFactory: NewsViewModelFactory): ViewModelProvider.Factory =
        viewModelFactory
}