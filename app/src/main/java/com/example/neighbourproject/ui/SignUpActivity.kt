package com.example.neighbourproject.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.neighbourproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpButton : Button
    lateinit var editTextEmail : EditText
    lateinit var editTextPassword : EditText
    lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        editTextEmail = findViewById(R.id.editTextEmailAddress)
        editTextPassword = findViewById(R.id.editTextPassword)
        signUpButton = findViewById(R.id.signUpButton)
        auth = Firebase.auth


        signUpButton.setOnClickListener {
          signUpUser()


        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    fun signUpUser(){

        if(editTextEmail.text.toString().isEmpty()){
            editTextEmail.error = "Please enter email"
            editTextPassword.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
            editTextEmail.error = "Please enter valid email"
            editTextEmail.requestFocus()
            return
        }
        if(editTextPassword.text.toString().isEmpty()){
            editTextPassword.error = "Enter a password"
            editTextPassword.requestFocus()
            return

        }
        auth.createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    startActivity(Intent(this, HomePageActivity::class.java))
                    finish()

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }




    }
}