package com.example.neighbourproject.ui.edit

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.contentValuesOf
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.core.net.toUri
import com.bumptech.glide.Glide

import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.Interest
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.neighbour.data.RelationshipStatus

import com.example.neighbourproject.ui.search.SearchActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.koin.android.ext.android.get
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

open class EditProfileActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "EditProfileActivity"
    }


    private val model: EditViewModel by viewModels()

    private val IMAGE_CHOOSE = 1000;
    private val REQUEST_GALLERY = 1001;
    private val REQUEST_CAMERA = 1

    lateinit var checkBox: ImageView
    lateinit var checkBox3: ImageView
    lateinit var imageView: ImageView
    lateinit var nameEditText: EditText
    lateinit var lastnameEditText: EditText
    lateinit var ageEditText: EditText
    lateinit var interestsEditText: EditText
    lateinit var genderSpinner: Spinner
    lateinit var relationshipSpinner: Spinner
    lateinit var saveButton: Button
    lateinit var galleryButton: Button
    lateinit var takePhotoButton: Button
    lateinit var emailEditText: EditText

    var profile: People? = null
    lateinit var db : DatabaseReference
    lateinit var addBtn : FloatingActionButton
    lateinit var interestRecyclerView : RecyclerView
    lateinit var interestList: MutableList<Interest>
    lateinit var interestAdapter : InterestAddAdapter
    lateinit var interest: Interest



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        title = "NeighbourProject"

        checkBox = findViewById(R.id.checkBox)
        imageView = findViewById(R.id.imageView)
        nameEditText = findViewById(R.id.nameEditText)
        lastnameEditText = findViewById(R.id.lastnameEditText)
        ageEditText = findViewById(R.id.ageEditText)
        genderSpinner = findViewById(R.id.genderSpinner)
        relationshipSpinner = findViewById(R.id.relationshipSpinner)
        saveButton = findViewById(R.id.button)
        galleryButton = findViewById(R.id.galleryButton)
        takePhotoButton = findViewById(R.id.takePhotoButton)
        emailEditText = findViewById(R.id.emailEditText)
        interestRecyclerView = findViewById(R.id.addInterestRecycler)
        //db = FirebaseDatabase.getInstance().getReference("neighbours")




        val storageReference = Firebase.storage.reference

        val adapter =
            ArrayAdapter<Gender>(this, android.R.layout.simple_spinner_item, Gender.values())
        genderSpinner.adapter = adapter

        val adapter2 = ArrayAdapter<RelationshipStatus>(
            this,
            android.R.layout.simple_spinner_item,
            RelationshipStatus.values()
        )
        relationshipSpinner.adapter = adapter2

        profile = model.getUserProfile()
        if (profile == null) {
            profile = People()
        } else {
            nameEditText.setText(profile?.firstName)
            lastnameEditText.setText(profile?.lastName)
            ageEditText.setText(profile?.age.toString())
            genderSpinner.setSelection(profile?.gender!!.ordinal)
            relationshipSpinner.setSelection(profile?.relationshipStatus!!.ordinal)
            emailEditText.setText(profile?.email)
            interestAdapter = InterestAddAdapter(profile?.interests?: mutableListOf())
            interestRecyclerView.adapter = interestAdapter

            Glide.with(this)
                .load(profile?.image)
                .into(imageView)
        }

        saveButton.setOnClickListener {
            profile?.firstName = nameEditText.text.toString()
            profile?.lastName = lastnameEditText.text.toString()
            profile?.age = ageEditText.text.toString().toInt()
            profile?.gender = Gender.valueOf(genderSpinner.selectedItem.toString())
            profile?.relationshipStatus =
                RelationshipStatus.valueOf(relationshipSpinner.selectedItem.toString())

            profile?.email = emailEditText.text.toString()
            /*profile?.interests.let {
                model.editUserProfile(People(interest.name))
            }*/
            profile?.interests

            upLoadImageToFirebaseStorage()

            startActivity(Intent(this, SearchActivity::class.java))
        }

        takePhotoButton.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d(TAG, "onCreate: Permission not granted")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_CAMERA
                )

            } else {
                Log.d(TAG, "onCreate: Permission is already granted!")
                capturePhoto()

            }

        }

        galleryButton.setOnClickListener {
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


    val REQUEST_CODE_CAMERA = 200

    fun capturePhoto() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA)
    }

    private fun upLoadImageToFirebaseStorage() {
        if (imageUri != null) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/Images/$filename")

            ref.putFile(imageUri!!)
                .addOnSuccessListener {
                    Log.d(
                        TAG,
                        "upLoadImageToFirebaseStorage: successfully uploaded image: ${it.metadata?.path}"
                    )

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "File Location: $it")

                        profile?.image = it.toString()
                        profile?.let {
                            model.editUserProfile(it)
                        }

                    }
                }
        }
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK )
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    var imageUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CAMERA && data != null){
        val bitMap = data.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitMap)

            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/Images/$filename")
            val baos = ByteArrayOutputStream()
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            ref.putBytes(data)
                .addOnSuccessListener {
                    Log.d(
                        TAG,
                        "upLoadImageToFirebaseStorage: successfully uploaded image: ${it.metadata?.path}"
                    )

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(TAG, "File Location: $it")

                        profile?.image = it.toString()
                        profile?.let {
                            model.editUserProfile(it)
                        }

                    }

                }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CHOOSE && data != null) {
            imageUri = data.data
            imageView.setImageURI(imageUri)
        }

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
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission Denied!")
            }
        }
        if (requestCode == REQUEST_GALLERY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "onRequestPermissionsResult: Permission Granted!")
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission Denied!")
            }
        }

    }
}

class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}

