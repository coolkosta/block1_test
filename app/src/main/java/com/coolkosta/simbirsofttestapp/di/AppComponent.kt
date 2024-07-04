package com.coolkosta.simbirsofttestapp.di

import com.coolkosta.simbirsofttestapp.presentation.screen.fragment.NewsFragment
import com.coolkosta.simbirsofttestapp.presentation.viewmodel.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, DomainModule::class])
interface AppComponent