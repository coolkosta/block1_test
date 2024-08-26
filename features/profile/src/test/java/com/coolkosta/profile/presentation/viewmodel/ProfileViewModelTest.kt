package com.coolkosta.profile.presentation.viewmodel

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.coolkosta.profile.presentation.screen.ProfileEvent
import com.coolkosta.profile.presentation.screen.ProfileSideEffect
import com.coolkosta.profile.presentation.viewmodel.ProfileViewModel.Companion.FILE_NAME
import com.coolkosta.profile.presentation.viewmodel.ProfileViewModel.Companion.MIME_TYPE_IMAGE_JPEG
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private val application = mockk<Application>()
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
    private val contentValues = mockk<ContentValues>()
    private var uri: Uri? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        viewModel = ProfileViewModel(application)
        Dispatchers.setMain(dispatcher)
        every {
            contentValues.apply {
                put(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    String.format(FILE_NAME, System.currentTimeMillis())
                )
                put(MediaStore.MediaColumns.MIME_TYPE, MIME_TYPE_IMAGE_JPEG)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
            }
        } returns mockk()
        every {
            application.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        } returns mockk()
        uri = createImageUri(contentValues)
    }

    @Test
    fun profileViewModel_sendEventTakePhotoCLicked_returnSideEffectRequestPermission() =
        runTest(dispatcher) {
            launch { viewModel.sendEvent(ProfileEvent.TakePhotoClicked) }
            assertEquals(ProfileSideEffect.RequestPermission, viewModel.sideEffect.first())
        }

    @Test
    fun profileViewModel_sendEventChosePhoto_returnSideEffectChosePhoto() = runTest(dispatcher) {
        launch { viewModel.sendEvent(ProfileEvent.ChoosePhoto) }
        assertEquals(ProfileSideEffect.ChoosePhoto, viewModel.sideEffect.first())
    }

    @Test
    fun profileViewModel_sendEventPermissionResult_returnSideEffectDeniedPermission() =
        runTest(dispatcher) {
            launch { viewModel.sendEvent(ProfileEvent.PermissionResult(false)) }
            assertEquals(ProfileSideEffect.DeniedPermission, viewModel.sideEffect.first())
        }

    @Test
    fun profileViewModel_sendEventPhotoChosen_getPhotoUri() = runTest(dispatcher) {
        viewModel.sendEvent(ProfileEvent.PhotoChosen(uri))
        assertEquals(uri, viewModel.state.value.photoUri)
    }

    @Test
    fun test() = runTest(dispatcher) {
        viewModel.sendEvent(ProfileEvent.PhotoChosen(uri))
        assertEquals(uri, viewModel.state.value.photoUri)
        viewModel.sendEvent(ProfileEvent.DeletePhoto)
        assertEquals(null, viewModel.state.value.photoUri)
    }

    private fun createImageUri(contentValues: ContentValues): Uri? {
        return application.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher to the original Main dispatcher
    }
}