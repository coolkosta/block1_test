package com.coolkosta.profile.di


import android.app.Application
import com.coolkosta.profile.presentation.screen.ProfileFragment
import com.coolkosta.profile.presentation.viewmodel.ProfileViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [ProfileDeps::class])
interface ProfileComponent {
    fun inject(fragment: ProfileFragment)
    fun profileViewModelFactory(): ProfileViewModelFactory
}

interface ProfileDeps {
    val application: Application
}
