package com.coolkosta.simbirsofttestapp.app

import android.app.Application
import com.coolkosta.simbirsofttestapp.di.AppComponent
import com.coolkosta.simbirsofttestapp.di.DaggerAppComponent

class App : Application() {



    override fun onCreate() {
        super.onCreate()
      val appComponent = DaggerAppComponent.create()
    }
}