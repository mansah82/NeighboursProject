package com.example.neighbourproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.neighbourproject.ui.edit.EditViewModel
import com.example.neighbourproject.ui.search.SearchActivity

open class EditProfileActivity : AppCompatActivity() {

    private val model : EditViewModel by viewModels()

    lateinit var checkBox: ImageView
    lateinit var checkBox2: ImageView
    lateinit var checkBox3: ImageView
    lateinit var imageView: ImageView
    lateinit var nameEditText: EditText
    lateinit var genderEditText: EditText
    lateinit var ageEditText: EditText
    lateinit var cityEditText: EditText
    lateinit var interestsEditText: EditText
    lateinit var genderSpinner: Spinner
    lateinit var saveButton : Button

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        title = "NeighbourProject"

        checkBox = findViewById(R.id.checkBox)
        checkBox2 = findViewById(R.id.checkBox2)
        checkBox3= findViewById(R.id.checkBox3)
        imageView = findViewById(R.id.imageView)
        nameEditText = findViewById(R.id.nameEditText)
        //genderEditText = findViewById(R.id.genderEditText)
        ageEditText = findViewById(R.id.ageEditText)
        cityEditText = findViewById(R.id.cityEditText)
        interestsEditText = findViewById(R.id.interestsEditText)
        genderSpinner = findViewById(R.id.genderSpinner)
        saveButton = findViewById(R.id.button)

        saveButton.setOnClickListener {
            //TODO update profile here
            model.editUserProfile(null)
            startActivity(Intent(this, SearchActivity::class.java))
        }

        nameEditText.doAfterTextChanged {
            if (nameEditText.length() > 3) {
                checkBox.visibility = View.VISIBLE
            } else {
                checkBox.visibility = View.INVISIBLE
            }
        }
        cityEditText.doAfterTextChanged {
            if (cityEditText.length() > 2) {
                checkBox2.visibility = View.VISIBLE
            } else {
                checkBox2.visibility = View.INVISIBLE
            }
        }

        interestsEditText.doAfterTextChanged {
            if (interestsEditText.length() > 1) {
                checkBox3.visibility = View.VISIBLE
            } else {
                checkBox3.visibility = View.INVISIBLE
            }
        }

        imageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view:View?, pos: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)

        }


    }




}









