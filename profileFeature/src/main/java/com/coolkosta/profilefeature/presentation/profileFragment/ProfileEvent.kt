package com.coolkosta.profilefeature.presentation.profileFragment

import android.net.Uri

sealed interface ProfileEvent {
    data object TakePhotoClicked : ProfileEvent
    data object ChoosePhoto : ProfileEvent
    data object MakePhoto : ProfileEvent
    data object DeletePhoto : ProfileEvent
    data class PermissionResult(val isGranted: Boolean) : ProfileEvent
    data class PhotoChosen(val uri: Uri?) : ProfileEvent
    data class PhotoTaken(val success: Boolean) : ProfileEvent
}

