package com.coolkosta.profilefeature.di


import androidx.lifecycle.ViewModelProvider
import com.coolkosta.profilefeature.presentation.profileFragment.ProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [ProfileDeps::class])
interface ProfileComponent{
    fun inject(fragment: ProfileFragment)
}

interface ProfileDeps {
    val appViewModelFactory : ViewModelProvider.Factory
}
