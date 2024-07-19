package com.coolkosta.news.di

import com.coolkosta.news.domain.interactor.CategoryInteractor
import com.coolkosta.news.domain.interactor.EventInteractor
import com.coolkosta.news.domain.repository.CategoryRepository
import com.coolkosta.news.domain.repository.EventRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideEventInteractor(repository: EventRepository): EventInteractor {
        return EventInteractor(repository = repository)
    }

    @Provides
    fun provideCategoryInteractor(repository: CategoryRepository): CategoryInteractor {
        return CategoryInteractor(repository = repository)
    }
}