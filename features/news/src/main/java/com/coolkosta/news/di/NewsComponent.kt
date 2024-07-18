package com.coolkosta.news.di

import android.content.Context
import com.coolkosta.news.presentation.screen.newsFilterFragment.NewsFilterFragment
import com.coolkosta.news.presentation.screen.newsFragment.NewsFragment
import com.coolkosta.news.presentation.viewmodel.NewsViewModelFactory
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Singleton
@Component(
    modules = [DataModule::class, DomainModule::class, NewsViewModelModule::class],
    dependencies = [NewsDeps::class]
)
interface NewsComponent {
    fun inject(fragment: NewsFragment)
    fun anotherInject(fragment: NewsFilterFragment)
    fun newsViewModelFactory(): NewsViewModelFactory
}

interface NewsDeps {
    val backgroundCoroutineDispatcher: CoroutineDispatcher
    val context: Context
}