package com.example.neighbourproject.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.neighbourproject.R
import com.example.neighbourproject.ui.homepage.HomePageActivity
import com.example.neighbourproject.user.RegisterStatus

class SignUpActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "SignUpActivity"
    }

    private val model: SignUpViewModel by viewModels()

    lateinit var signUpButton : Button
    lateinit var editTextEmail : EditText
    lateinit var editTextPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editTextEmail = findViewById(R.id.editTextEmailAddress)
        editTextPassword = findViewById(R.id.editTextPassword)
        signUpButton = findViewById(R.id.signUpButton)

        val userResisterObserver = Observer<RegisterStatus>{
            if(it.success != null){
                startActivity(Intent(this, HomePageActivity::class.java))
                finish()
            }else{
                Toast.makeText(baseContext, "Failed: ${it.failed}",
                    Toast.LENGTH_SHORT).show()
            }
        }
        model.getUserRegisterUpdate().observe(this@SignUpActivity, userResisterObserver)

        signUpButton.setOnClickListener {
          signUpUser()
        }
    }

    private fun signUpUser(){
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
        model.resisterUser(editTextEmail.text.toString(), editTextPassword.text.toString())
    }
}