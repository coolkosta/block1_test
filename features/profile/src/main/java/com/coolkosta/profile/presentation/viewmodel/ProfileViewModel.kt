package com.coolkosta.profile.presentation.viewmodel

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.coolkosta.profile.presentation.screen.ProfileEvent
import com.coolkosta.profile.presentation.screen.ProfileSideEffect
import com.coolkosta.profile.presentation.screen.ProfileViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private val _state = MutableStateFlow(ProfileViewState())
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<ProfileSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var photoUri: Uri? = null

    fun sendEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.TakePhotoClicked -> requestCameraPermission()
            is ProfileEvent.ChoosePhoto -> choosePhoto()
            is ProfileEvent.MakePhoto -> makePhoto()
            is ProfileEvent.DeletePhoto -> deletePhoto()
            is ProfileEvent.PermissionResult -> handlePermissionResult(event.isGranted)
            is ProfileEvent.PhotoChosen -> handlePhotoChosen(event.uri)
            is ProfileEvent.PhotoTaken -> handlePhotoTaken(event.success)
        }
    }

    private fun requestCameraPermission() {
        _sideEffect.trySend(ProfileSideEffect.RequestPermission)
    }

    private fun choosePhoto() {
        _sideEffect.trySend(ProfileSideEffect.ChoosePhoto)
    }

    private fun makePhoto() {
        photoUri = createImageUri()
        _state.update { _state.value.copy(photoUri = photoUri) }
        _sideEffect.trySend(ProfileSideEffect.TakePhoto(photoUri!!))
    }

    private fun deletePhoto() {
        _state.update { _state.value.copy(photoUri = null) }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        _state.update { _state.value.copy(isPermissionGranted = isGranted) }
        if (isGranted) {
            makePhoto()
        } else {
            _sideEffect.trySend(ProfileSideEffect.DeniedPermission)
        }
    }

    private fun handlePhotoChosen(uri: Uri?) {
        _state.update { _state.value.copy(photoUri = uri) }
    }

    private fun handlePhotoTaken(success: Boolean) {
        if (success) {
            _state.update { _state.value.copy(photoUri = photoUri) }
        }
    }

    private fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                String.format(FILE_NAME, System.currentTimeMillis())
            )
            put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE_IMAGE_JPEG)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }
        return application.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    companion object {
        const val FILE_NAME = "JPEG_%s.jpg"
        const val MIME_TYPE_IMAGE_JPEG = "image/jpeg"
        const val MIME_TYPE_IMAGE = "image/*"
    }
}
