package com.coolkosta.simbirsofttestapp.util

import androidx.fragment.app.Fragment
import com.coolkosta.simbirsofttestapp.app.App
import com.coolkosta.simbirsofttestapp.di.AppComponent

fun Fragment.getAppComponent(): AppComponent =
    (requireContext().applicationContext as App).appComponent