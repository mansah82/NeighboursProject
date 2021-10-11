package com.example.neighbourproject.ui.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.neighbourproject.EditProfileActivity
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.ActivityHomePageBinding
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.ui.SignUpActivity
import com.example.neighbourproject.ui.search.SearchActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomePageActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "HomePageActivity"
    }

    private val model: HomePageViewModel by viewModels()

    private lateinit var binding: ActivityHomePageBinding

    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userProfileObserver = Observer<People?> {
            if (model.signedIn) {
                if (it == null) {
                    //Need to edit
                    Log.d(TAG, "userProfileObserver - need profile")
                    startActivity(Intent(this@HomePageActivity, EditProfileActivity::class.java))
                } else {
                    // We have profile, jump into search
                    Log.d(TAG, "userProfileObserver - Have profile")
                    startActivity(Intent(this@HomePageActivity, SearchActivity::class.java))
                }
            }
        }

        binding.loginButton.setOnClickListener {

            model.getUserProfileUpdate().observe(this@HomePageActivity, userProfileObserver)

            auth.signInWithEmailAndPassword(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        auth.currentUser?.let {
                            model.setSignedInUser(it.uid)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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