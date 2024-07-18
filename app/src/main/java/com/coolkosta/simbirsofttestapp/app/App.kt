package com.coolkosta.simbirsofttestapp.app

import android.app.Application
import com.coolkosta.news.di.DaggerNewsComponent
import com.coolkosta.news.di.NewsComponent
import com.coolkosta.news.di.NewsComponentProvider
import com.coolkosta.profile.di.DaggerProfileComponent
import com.coolkosta.profile.di.ProfileComponent
import com.coolkosta.profile.di.ProfileComponentProvider
import com.coolkosta.simbirsofttestapp.di.AppComponent
import com.coolkosta.simbirsofttestapp.di.AppModule
import com.coolkosta.simbirsofttestapp.di.DaggerAppComponent


class App : Application(), ProfileComponentProvider, NewsComponentProvider {

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

    override fun getNewsComponent(): NewsComponent {
        return DaggerNewsComponent.builder()
            .newsDeps(appComponent)
            .build()
    }
}