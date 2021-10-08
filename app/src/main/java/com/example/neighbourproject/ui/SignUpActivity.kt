package com.example.neighbourproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import com.example.neighbourproject.R

class SignUpActivity : AppCompatActivity() {
    lateinit var signUpButton : Button
    lateinit var editTextEmail : EditText
    lateinit var editTextPassword : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        editTextEmail = findViewById(R.id.editTextEmailAddress)
        editTextPassword = findViewById(R.id.editTextPassword)
        signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {

            if(editTextEmail.text.toString().isEmpty()){
                editTextEmail.error = "Please enter email"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()){
                editTextEmail.error = "Please enter valid email"
                editTextEmail.requestFocus()
            }
            if(editTextPassword.text.toString().isEmpty()){
                editTextPassword.error = "Enter a password"
                editTextPassword.requestFocus()

            }

        }
    }
}