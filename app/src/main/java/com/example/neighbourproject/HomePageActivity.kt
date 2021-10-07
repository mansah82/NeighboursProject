package com.example.neighbourproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

lateinit var registerButton: TextView
lateinit var usernameEdit: EditText
lateinit var passwordEdit: EditText

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        registerButton = findViewById(R.id.registerTextview)
        usernameEdit = findViewById(R.id.usernameEditText)
        passwordEdit = findViewById(R.id.passwordEditText)

        emailCheckCorrect()




        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener{
            //Starta skapaaktivitet
        }


    }

    fun emailCheckCorrect(){ //Work in progress

       val typo =  usernameEdit.getText().toString()


        if (typo == "hej") {
            val mail = usernameEdit.getText().toString();
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                usernameEdit.setError("Please enter a valid username");
            }
            else {

                usernameEdit.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_launcher_background, 0)

            }
        }
    }



}