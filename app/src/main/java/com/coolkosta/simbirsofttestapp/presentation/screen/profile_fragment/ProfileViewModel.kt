package com.coolkosta.simbirsofttestapp.presentation.screen.profile_fragment

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val application: Application) : ViewModel() {

    private val _viewState = MutableLiveData<ProfileViewState>()
    val viewState: LiveData<ProfileViewState> = _viewState

    private val _sideEffect = MutableLiveData<ProfileSideEffect>()
    val sideEffect: LiveData<ProfileSideEffect> = _sideEffect

    private var photoUri: Uri? = null

    init {
        _viewState.value = ProfileViewState()
    }

    fun sendEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.RequestCameraPermission -> requestCameraPermission()
            is ProfileEvent.ChoosePhoto -> choosePhoto()
            is ProfileEvent.MakePhoto -> makePhoto()
            is ProfileEvent.DeletePhoto -> deletePhoto()
            is ProfileEvent.PermissionResult -> handlePermissionResult(event.isGranted)
            is ProfileEvent.PhotoChosen -> handlePhotoChosen(event.uri)
            is ProfileEvent.PhotoTaken -> handlePhotoTaken(event.success)
        }
    }

    private fun requestCameraPermission() {
        _sideEffect.value = ProfileSideEffect.RequestPermission
    }

    private fun choosePhoto() {
        _sideEffect.value = ProfileSideEffect.ChoosePhoto
    }

    private fun makePhoto() {
        photoUri = createImageUri()
        _viewState.value = _viewState.value?.copy(photoUri = photoUri)
        _sideEffect.value = ProfileSideEffect.TakePhoto(photoUri!!)
    }

    private fun deletePhoto() {
        photoUri = null
        _viewState.value = ProfileViewState(photoUri = null)
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        _viewState.value = _viewState.value?.copy(isPermissionGranted = isGranted)
        if (isGranted) {
            makePhoto()
        } else {
            _sideEffect.value = ProfileSideEffect.DeniedPermission
        }
    }

    private fun handlePhotoChosen(uri: Uri?) {
        _viewState.value = _viewState.value?.copy(photoUri = uri)
    }

    private fun handlePhotoTaken(success: Boolean) {
        if (success) {
            _viewState.value = _viewState.value?.copy(photoUri = photoUri)
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
