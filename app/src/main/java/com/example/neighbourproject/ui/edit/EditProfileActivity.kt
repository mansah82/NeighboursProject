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

open class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "EditProfileActivity"
        private const val REQUEST_GALLERY = 1001
        private const val REQUEST_CAMERA = 2002
    }

    private val model: EditViewModel by viewModels()

    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var profile: People

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            // TODO remove this to make stuff be stored
            profile.image = ""

            model.editUserProfile(profile)

            //TODO decide when to push image to firestore
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

    private val cameraResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            result.data?.let { intent ->
                Log.d(TAG, "Camera: $intent")
                val bitMap = intent.extras?.get("data") as Bitmap
                binding.circularPhoto.setImageBitmap(bitMap)
                profile.image = model.writeImage(bitMap)
            }
        }
    }

    private fun capturePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResultLauncher.launch(intent)
    }

    private val galleryResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            result.data?.let { intent ->
                Log.d(TAG, "Gallery: $intent")
                val uri = intent.data

                if(uri != null) {
                    binding.circularPhoto.setImageURI(uri)
                    profile.image = model.writeImage(uri)
                }
            }
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryResultLauncher.launch(intent)
    }

    /*
    https://firebasestorage.googleapis.com/v0/b/neighbourproject.appspot.com/o/Images%2F07a3cd50-8e70-4195-9c49-140568f2b556?alt=media&token=8c5a3349-8bcb-4aa4-8085-bc4e55567932
    */

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
