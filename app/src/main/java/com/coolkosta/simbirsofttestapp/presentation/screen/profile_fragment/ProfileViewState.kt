package com.coolkosta.simbirsofttestapp.presentation.screen.profile_fragment

import android.net.Uri

data class ProfileViewState(
    val photoUri: Uri? = null,
    val isPermissionGranted: Boolean = false,
)
