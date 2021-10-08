package com.example.neighbourproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.neighbourproject.R
import com.example.neighbourproject.ui.search.SearchActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomePageActivity : AppCompatActivity() {

    private lateinit var registerButton: TextView
    private lateinit var usernameEdit: EditText
    private lateinit var passwordEdit: EditText
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val auth = Firebase.auth


        auth.createUserWithEmailAndPassword("email@jfoe.se", "hejhejhej19")


        registerButton = findViewById(R.id.registerTextview)
        usernameEdit = findViewById(R.id.usernameEditText)
        passwordEdit = findViewById(R.id.passwordEditText)
        val TAG = "!!!"

        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(usernameEdit.toString(), passwordEdit.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
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