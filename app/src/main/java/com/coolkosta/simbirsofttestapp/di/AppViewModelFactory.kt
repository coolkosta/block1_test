package com.coolkosta.simbirsofttestapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment.LoginFragmentViewModel
import javax.inject.Inject
import javax.inject.Provider

class AppViewModelFactory @Inject constructor(
    loginViewModelProvider: Provider<LoginFragmentViewModel>
) : ViewModelProvider.Factory {
    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        LoginFragmentViewModel::class.java to loginViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return providers[modelClass]!!.get() as T
    }
}