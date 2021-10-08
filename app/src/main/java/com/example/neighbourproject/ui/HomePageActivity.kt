package com.example.neighbourproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.ActivityHomePageBinding
import com.example.neighbourproject.ui.search.SearchActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomePageActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityHomePageBinding

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = findViewById<Button>(R.id.loginButton)
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.registerTextview.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.usernameEditText.doAfterTextChanged {

        }

        binding.registerTextview.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun emailCheckCorrect() { //Work in progress
        val typo = binding.usernameEditText.text.toString()

        if (typo == "hej") {
            val mail = binding.usernameEditText.text.toString();
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                binding.usernameEditText.error = "Please enter a valid username";
            } else {

                binding.usernameEditText.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_launcher_background, 0
                )
            }
        }
    }
}