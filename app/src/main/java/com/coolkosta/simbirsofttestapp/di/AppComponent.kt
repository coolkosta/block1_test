package com.coolkosta.simbirsofttestapp.di

import com.coolkosta.simbirsofttestapp.presentation.viewmodel.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun viewModelsFactory(): ViewModelFactory
}