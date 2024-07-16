package com.coolkosta.simbirsofttestapp.di

import androidx.lifecycle.ViewModelProvider
import com.coolkosta.simbirsofttestapp.presentation.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
     fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory =
         viewModelFactory
}