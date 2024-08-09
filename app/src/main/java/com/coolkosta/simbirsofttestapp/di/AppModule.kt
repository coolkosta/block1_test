package com.coolkosta.simbirsofttestapp.di

import android.app.Application
import android.content.Context
import android.content.Intent
import com.coolkosta.simbirsofttestapp.util.IntentAppActivity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideBackgroundDispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideActivityIntent(): Intent =
        IntentAppActivity(application).getIntentActivity()

}