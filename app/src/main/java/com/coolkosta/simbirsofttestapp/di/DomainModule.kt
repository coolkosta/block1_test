package com.coolkosta.simbirsofttestapp.di

import com.coolkosta.simbirsofttestapp.domain.interactor.CategoryInteractor
import com.coolkosta.simbirsofttestapp.domain.interactor.EventInteractor
import com.coolkosta.simbirsofttestapp.domain.repository.CategoryRepository
import com.coolkosta.simbirsofttestapp.domain.repository.EventRepository
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