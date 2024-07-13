package com.coolkosta.simbirsofttestapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment.NewsFilterViewModel
import com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment.NewsViewModel
import com.coolkosta.profilefeature.presentation.profileFragment.ProfileViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    newsViewModelProvider: Provider<NewsViewModel>,
    newsFilterViewModelProvider: Provider<NewsFilterViewModel>,
    profileViewModelProvider: Provider<com.coolkosta.profilefeature.presentation.profileFragment.ProfileViewModel>
) : ViewModelProvider.Factory {
    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        NewsViewModel::class.java to newsViewModelProvider,
        NewsFilterViewModel::class.java to newsFilterViewModelProvider,
        com.coolkosta.profilefeature.presentation.profileFragment.ProfileViewModel::class.java to profileViewModelProvider
    )

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return providers[modelClass]!!.get() as T
    }
}