package com.example.neighbourproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.neighbourproject.EditProfileActivity
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.ActivityHomePageBinding
import com.example.neighbourproject.ui.search.SearchActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomePageActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "HomePageActivity"
    }

    private lateinit var  binding : ActivityHomePageBinding

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = Firebase.auth
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            Log.d(TAG, "Logging in: ${binding.usernameEditText.text.toString()} - ${binding.passwordEditText.text.toString()}")
            auth.signInWithEmailAndPassword(binding.usernameEditText.text.toString(), binding.passwordEditText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, EditProfileActivity::class.java)
                        startActivity(intent)

                    } else {
                        binding.usernameEditText.error = "Email is incorrect"
                        binding.passwordEditText.error = "Password is incorrect"
                    }
                }

        }
        binding.usernameEditText.doAfterTextChanged {
            checkIfCorrectEmailFormat()
        }
        binding.passwordEditText.doAfterTextChanged {
            checkIfCorrectPasswordFormat()
        }

        binding.registerTextview.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    fun checkIfCorrectEmailFormat(){
        if(binding.usernameEditText.text.toString().contains("@", true) &&
            binding.usernameEditText.text.toString().contains(".", true)){

        } else{
            binding.usernameEditText.error = "Please enter a valid email"
        }
    }
    fun checkIfCorrectPasswordFormat() {
        if (binding.passwordEditText.text.length < 5) {
            binding.passwordEditText.error = "Password needs to contain 6 letters or more"
        }
    }

}
