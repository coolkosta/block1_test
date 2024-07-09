package com.coolkosta.simbirsofttestapp.presentation.screen.profile_fragment

import android.net.Uri

sealed class ProfileSideEffect {
    data object RequestPermission : ProfileSideEffect()
    data object DeniedPermission: ProfileSideEffect()
    data object ChoosePhoto : ProfileSideEffect()
    data class TakePhoto(val uri: Uri) : ProfileSideEffect()
}
