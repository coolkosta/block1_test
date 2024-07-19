package com.coolkosta.news.di

import android.content.Context
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, DomainModule::class],
    dependencies = [NewsDeps::class]
)
fun interface NewsComponent {
    fun newsViewModelFactory(): NewsViewModelFactory
}

interface NewsDeps {
    val backgroundCoroutineDispatcher: CoroutineDispatcher
    val context: Context
}