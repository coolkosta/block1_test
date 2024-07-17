package com.coolkosta.profile.presentation.screen

import android.net.Uri

sealed interface ProfileSideEffect {
    data object RequestPermission : ProfileSideEffect
    data object DeniedPermission: ProfileSideEffect
    data object ChoosePhoto : ProfileSideEffect
    data class TakePhoto(val uri: Uri) : ProfileSideEffect
}
