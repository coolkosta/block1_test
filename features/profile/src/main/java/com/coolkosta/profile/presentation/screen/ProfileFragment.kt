package com.coolkosta.profile.presentation.screen

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coolkosta.profile.R
import com.coolkosta.profile.di.ProfileComponentProvider
import com.coolkosta.profile.presentation.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private lateinit var choosePhotoLauncher: ActivityResultLauncher<String>

    private val viewModel: ProfileViewModel by viewModels {
        (requireActivity().application as ProfileComponentProvider)
            .getProfileComponent()
            .profileViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        choosePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                viewModel.sendEvent(ProfileEvent.PhotoChosen(uri))
            }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                viewModel.sendEvent(ProfileEvent.PermissionResult(isGranted))
            }

        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                viewModel.sendEvent(ProfileEvent.PhotoTaken(success))
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.profile_iv)
        imageView.setOnClickListener { showChooseDialog() }
        observeProfileViewModel()
    }

    private fun observeProfileViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        state.photoUri?.let {
                            imageView.setImageURI(it)
                        } ?: run {
                            imageView.setImageResource(R.drawable.ic_empty_photo)
                            imageView.adjustViewBounds = true
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { action ->
                        when (action) {
                            is ProfileSideEffect.RequestPermission -> requestPermissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )

                            is ProfileSideEffect.ChoosePhoto -> choosePhotoLauncher.launch(
                                ProfileViewModel.MIME_TYPE_IMAGE
                            )

                            is ProfileSideEffect.TakePhoto -> takePictureLauncher.launch(action.uri)
                            is ProfileSideEffect.DeniedPermission -> {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.reminder_photo_permission_toast),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showChooseDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_view, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.choose_photo_item).setOnClickListener {
            viewModel.sendEvent(ProfileEvent.ChoosePhoto)
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.make_photo_item).setOnClickListener {
            viewModel.sendEvent(ProfileEvent.TakePhotoClicked)
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.delete_item).setOnClickListener {
            viewModel.sendEvent(ProfileEvent.DeletePhoto)
            dialog.dismiss()
        }

        dialog.show()
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}



