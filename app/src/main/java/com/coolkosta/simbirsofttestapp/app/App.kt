package com.coolkosta.simbirsofttestapp.app

import android.app.Application
import com.coolkosta.profilefeature.di.DaggerProfileComponent
import com.coolkosta.profilefeature.di.ProfileComponent
import com.coolkosta.profilefeature.di.ProfileComponentProvider
import com.coolkosta.simbirsofttestapp.di.AppComponent
import com.coolkosta.simbirsofttestapp.di.AppModule
import com.coolkosta.simbirsofttestapp.di.DaggerAppComponent


class App : Application(), ProfileComponentProvider {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun getProfileComponent(): ProfileComponent {
        return DaggerProfileComponent.builder()
            .profileDeps(appComponent)
            .build()
    }
}