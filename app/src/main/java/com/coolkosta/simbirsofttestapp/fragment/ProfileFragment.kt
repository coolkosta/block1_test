package com.coolkosta.simbirsofttestapp.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.coolkosta.simbirsofttestapp.R

class ProfileFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private lateinit var choosePhotoLauncher: ActivityResultLauncher<String>
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        choosePhotoLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let { imageUri ->
                    photoUri = imageUri
                    imageView.setImageURI(photoUri)
                }
            }

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                photoUri = createImageUri()
                photoUri?.let {
                    takePictureLauncher.launch(it)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.reminder_photo_permission_toast),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        takePictureLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    photoUri?.let {
                        imageView.setImageURI(it)
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.profile_iv)
        imageView.setOnClickListener { showChooseDialog() }

        view.findViewById<ImageView>(R.id.ic_friend_1).apply {
            loadIcon(R.drawable.avatar_3, this)
        }
        view.findViewById<ImageView>(R.id.ic_friend_2).apply {
            loadIcon(R.drawable.avatar_2, this)
        }
        view.findViewById<ImageView>(R.id.ic_friend_3).apply {
            loadIcon(R.drawable.avatar_1, this)
        }
    }

    private fun loadIcon(@DrawableRes drawableRes: Int, view: ImageView) {
        Glide
            .with(requireContext())
            .load(drawableRes)
            .into(view)
    }

    private fun showChooseDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_view, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.choose_photo_item).setOnClickListener {
            choosePhotoLauncher.launch(MIME_TYPE_IMAGE)
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.make_photo_item).setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            dialog.dismiss()
        }

        dialogView.findViewById<TextView>(R.id.delete_item).setOnClickListener {
            deletePhoto()
            dialog.dismiss()
        }

        dialog.show()
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
        return requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }

    private fun deletePhoto() {
        imageView.apply {
            setImageResource(R.drawable.ic_emty_photo)
            adjustViewBounds = true
        }
    }

    companion object {
        const val FILE_NAME = "JPEG_%s.jpg"
        const val MIME_TYPE_IMAGE_JPEG = "image/jpeg"
        const val MIME_TYPE_IMAGE = "image/*"
        fun newInstance() = ProfileFragment()
    }
}