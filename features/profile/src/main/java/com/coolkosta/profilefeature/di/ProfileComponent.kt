package com.coolkosta.profilefeature.di


import androidx.lifecycle.ViewModelProvider
import com.coolkosta.profilefeature.presentation.profileFragment.ProfileFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [ProfileDeps::class])
interface ProfileComponent{
    fun inject(fragment: ProfileFragment)
    fun viewModelsFactory(): ViewModelProvider.Factory
}

interface ProfileDeps {
    val viewModelsFactory : ViewModelProvider.Factory
}
