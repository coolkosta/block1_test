package com.coolkosta.profile.di


import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [ProfileDeps::class])
fun interface ProfileComponent {
    fun profileViewModelFactory(): ProfileViewModelFactory
}

interface ProfileDeps {
    val application: Application
}
