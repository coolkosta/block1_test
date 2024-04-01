package com.coolkosta.simbirsofttestapp

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
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    "Для съемки фото необходимо разрешение на использование камеры",
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

    }

    private fun showChooseDialog() {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.cusom_dialog_view, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.choose_photo_item).setOnClickListener {
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
            put(MediaStore.MediaColumns.DISPLAY_NAME, "JPEG_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
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
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}