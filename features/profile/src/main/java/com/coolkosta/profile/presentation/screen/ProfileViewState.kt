package com.coolkosta.profile.presentation.screen

import android.net.Uri

data class ProfileViewState(
    val photoUri: Uri? = null,
    val isPermissionGranted: Boolean = false,
)
