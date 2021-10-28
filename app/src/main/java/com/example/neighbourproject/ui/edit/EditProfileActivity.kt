package com.example.neighbourproject.ui.edit

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.neighbourproject.databinding.ActivityEditProfileBinding
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.neighbour.data.RelationshipStatus
import com.example.neighbourproject.ui.search.SearchActivity
import kotlin.math.min
import kotlin.math.roundToInt

open class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "EditProfileActivity"
        private const val REQUEST_GALLERY = 1001
        private const val REQUEST_CAMERA = 2002
        private const val MAX_IMAGE_SIZE = 124.0
    }

    private val model: EditViewModel by viewModels()

    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var profile: People

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate")

        profile = model.getUserProfile() ?: People()

        if (profile.image != "") {
            model.loadImage(this, profile.image, binding.circularPhoto)
        }

        binding.genderSpinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Gender.values())

        binding.relationshipSpinner.adapter =
            ArrayAdapter(
                this, android.R.layout.simple_spinner_item, RelationshipStatus.values()
            )

        binding.nameEditText.setText(profile.firstName)
        binding.lastnameEditText.setText(profile.lastName)
        binding.ageEditText.setText(profile.age.toString())
        binding.genderSpinner.setSelection(profile.gender.ordinal)
        binding.relationshipSpinner.setSelection(profile.relationshipStatus.ordinal)

        binding.emailEditText.setText(profile.email)

        binding.addInterestRecycler.adapter = InterestAddAdapter(profile, model)

        binding.saveButton.setOnClickListener {
            profile.firstName = binding.nameEditText.text.toString()
            profile.lastName = binding.lastnameEditText.text.toString()
            profile.age = binding.ageEditText.text.toString().toInt()
            profile.gender = Gender.valueOf(binding.genderSpinner.selectedItem.toString())
            profile.relationshipStatus =
                RelationshipStatus.valueOf(binding.relationshipSpinner.selectedItem.toString())

            profile.email = binding.emailEditText.text.toString()

            model.editUserProfile(profile)

            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        }

        //TODO perhaps a selector to select image
        binding.takePhotoButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_CAMERA
                )
            } else {
                capturePhoto()
            }
        }

        binding.circularPhoto.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_GALLERY
                )
            } else {
                chooseImageGallery()
            }
        }
    }

    private val cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    Log.d(TAG, "Camera: $intent")
                    val bitmap = intent.extras?.get("data") as Bitmap
                    handleImage(bitmap)
                }
            }
        }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResultLauncher.launch(intent)
    }

    private val galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let { intent ->
                    Log.d(TAG, "Gallery: $intent")
                    val uri = intent.data

                    if (uri != null) {
                        //TODO fix deprecated
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        handleImage(bitmap)
                    }
                }
            }
        }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResultLauncher.launch(intent)
    }

    private fun handleImage(bitmap: Bitmap) {
        val ratio: Float = min(
            MAX_IMAGE_SIZE.toFloat() / bitmap.width,
            MAX_IMAGE_SIZE.toFloat() / bitmap.height
        )
        val width = (ratio * bitmap.width).roundToInt()
        val height = (ratio * bitmap.height).roundToInt()

        val newBitmap = Bitmap.createScaledBitmap(
            bitmap, width,
            height, false
        )

        binding.circularPhoto.setImageBitmap(newBitmap)
        Log.d(
            TAG,
            "Bitmap: ${bitmap.byteCount} bytes, ${bitmap.height}x${bitmap.width} height x width"
        )
        Log.d(
            TAG,
            "newBitmap: ${newBitmap.byteCount} bytes, ${newBitmap.height}x${newBitmap.width} height x width"
        )

        //Let garbage take care of the old image
        bitmap.recycle()

        profile.image = model.writeImage(newBitmap)

        if(profile.image != ""){
            model.editUserProfile(profile)
        }

        Log.d(TAG, "Profile.image: ${profile.image}")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted!")
                capturePhoto()
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission Denied!")
            }
        }
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted!")
                chooseImageGallery()
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission Denied!")
            }
        }
    }
}
