package com.coolkosta.simbirsofttestapp.presentation.screen.profileFragment

import android.net.Uri

data class ProfileViewState(
    val photoUri: Uri? = null,
    val isPermissionGranted: Boolean = false,
)
