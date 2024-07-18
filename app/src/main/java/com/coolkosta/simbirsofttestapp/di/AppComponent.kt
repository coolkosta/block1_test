package com.coolkosta.simbirsofttestapp.di

import com.coolkosta.news.di.NewsDeps
import com.coolkosta.profile.di.ProfileDeps
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : ProfileDeps, NewsDeps {
}