package com.coolkosta.news.di

import android.app.Application
import android.content.Context
import com.coolkosta.core.util.ActivityUtils
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, DomainModule::class],
    dependencies = [NewsDeps::class]
)
interface NewsComponent {
    fun newsViewModelFactory(): NewsViewModelFactory
    fun activityIntent(): ActivityUtils
}

interface NewsDeps {
    val backgroundCoroutineDispatcher: CoroutineDispatcher
    val context: Context
    val application: Application
    val activityIntent: ActivityUtils
}