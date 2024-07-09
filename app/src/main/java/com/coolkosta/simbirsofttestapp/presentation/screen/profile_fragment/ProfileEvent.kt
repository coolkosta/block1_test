package com.coolkosta.simbirsofttestapp.presentation.screen.profile_fragment

import android.net.Uri

sealed class ProfileEvent {
    data object RequestCameraPermission : ProfileEvent()
    data object ChoosePhoto : ProfileEvent()
    data object MakePhoto : ProfileEvent()
    data object DeletePhoto : ProfileEvent()
    data class PermissionResult(val isGranted: Boolean) : ProfileEvent()
    data class PhotoChosen(val uri: Uri?) : ProfileEvent()
    data class PhotoTaken(val success: Boolean) : ProfileEvent()
}

