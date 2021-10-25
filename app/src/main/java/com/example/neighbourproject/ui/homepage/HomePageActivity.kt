package com.example.neighbourproject.ui.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.neighbourproject.ui.edit.EditProfileActivity
import com.example.neighbourproject.databinding.ActivityHomePageBinding
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.ui.chat.LatestMessageActivity
import com.example.neighbourproject.ui.chat.NewMessageActivity
import com.example.neighbourproject.ui.signup.SignUpActivity
import com.example.neighbourproject.ui.search.SearchActivity
import com.example.neighbourproject.user.LoginStatus

class HomePageActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "HomePageActivity"
    }

    private val model: HomePageViewModel by viewModels()

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userLoginObserver = Observer<LoginStatus> {
            Log.d(TAG, "Login observer: ${it.success}-${it.failed}")
            if (it.success != null) {
                model.setSignedInUser(it.success)
            } else {
                if (it.failed != null) {
                    binding.usernameEditText.error = it.failed
                    binding.passwordEditText.error = it.failed
                }
            }
        }
        model.getUserLoginUpdate().observe(this@HomePageActivity, userLoginObserver)

        val userProfileObserver = Observer<People?> {
            if (model.isSignedIn()) {
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
        model.getUserProfileUpdate().observe(this@HomePageActivity, userProfileObserver)

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LatestMessageActivity::class.java)
            startActivity(intent)
           //if (checkIfCorrectEmailFormat() && checkIfCorrectPasswordFormat())
            //model.signInUser(
                //binding.usernameEditText.text.toString(),
               // binding.passwordEditText.text.toString() )
        }


        binding.usernameEditText.doAfterTextChanged {
            checkIfCorrectEmailFormat()
        }
        binding.passwordEditText.doAfterTextChanged {
            checkIfCorrectPasswordFormat()
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfCorrectEmailFormat(): Boolean {
        if (!binding.usernameEditText.text.toString().contains("@", true) ||
            !binding.usernameEditText.text.toString().contains(".", true)
        ) {
            binding.usernameEditText.error = "Please enter a valid email"
            return false
        }
        return true
    }

    private fun checkIfCorrectPasswordFormat(): Boolean {
        if (binding.passwordEditText.text.length < 5) {
            binding.passwordEditText.error = "Password needs to contain 6 letters or more"
            return false
        }
        return true

    }

}
