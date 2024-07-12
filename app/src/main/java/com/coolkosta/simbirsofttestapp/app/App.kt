package com.coolkosta.simbirsofttestapp.app

import android.app.Application
import com.coolkosta.simbirsofttestapp.di.AppComponent
import com.coolkosta.simbirsofttestapp.di.AppModule
import com.coolkosta.simbirsofttestapp.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}