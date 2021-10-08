package com.example.neighbourproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.example.neighbourproject.R
import com.example.neighbourproject.ui.search.SearchActivity


class HomePageActivity : AppCompatActivity() {

    private lateinit var registerButton: TextView
    private lateinit var usernameEdit: EditText
    private lateinit var passwordEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        registerButton = findViewById(R.id.registerTextview)
        usernameEdit = findViewById(R.id.usernameEditText)
        passwordEdit = findViewById(R.id.passwordEditText)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        usernameEdit.doAfterTextChanged {

        }
    }

    fun emailCheckCorrect() { //Work in progress
        val typo = usernameEdit.getText().toString()

        if (typo == "hej") {
            val mail = usernameEdit.getText().toString();
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                usernameEdit.setError("Please enter a valid username");
            } else {

                usernameEdit.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    R.drawable.ic_launcher_background, 0
                )
            }
        }
    }
}