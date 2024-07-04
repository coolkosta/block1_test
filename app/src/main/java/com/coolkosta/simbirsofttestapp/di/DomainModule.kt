package com.coolkosta.simbirsofttestapp.di

import com.coolkosta.simbirsofttestapp.domain.interactor.GetCategoriesInteractor
import com.coolkosta.simbirsofttestapp.domain.interactor.GetEventsInteractor
import com.coolkosta.simbirsofttestapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetEventsInteractor(repository: NewsRepository): GetEventsInteractor {
        return GetEventsInteractor(repository = repository)
    }
@Provides
    fun provideGetCategoriesInteractor(repository: NewsRepository): GetCategoriesInteractor {
        return GetCategoriesInteractor(repository = repository)
    }
}