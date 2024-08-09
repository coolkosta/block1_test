package com.coolkosta.news.di

import android.app.Application
import android.content.Context
import android.content.Intent
import com.coolkosta.news.util.IntentActivity
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
    fun intentActivity(): IntentActivity
}

interface NewsDeps {
    val backgroundCoroutineDispatcher: CoroutineDispatcher
    val context: Context
    val application: Application
    val activityIntent: Intent
}