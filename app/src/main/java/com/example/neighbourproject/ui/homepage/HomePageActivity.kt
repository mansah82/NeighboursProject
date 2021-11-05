package com.example.neighbourproject.ui.homepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.neighbourproject.R
import com.example.neighbourproject.ui.edit.EditProfileActivity
import com.example.neighbourproject.databinding.ActivityHomePageBinding
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.ui.signup.SignUpActivity
import com.example.neighbourproject.ui.search.SearchActivity
import com.example.neighbourproject.user.EvaluationHelper
import com.example.neighbourproject.user.ExtrasKey
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

        intent.getStringExtra(ExtrasKey.KEY_USER_NAME)?.let {
            binding.usernameEditText.setText(it)
        }
        intent.getStringExtra(ExtrasKey.KEY_PASSWORD)?.let {
            binding.passwordEditText.setText(it)
        }

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

        binding.usernameEditText.doAfterTextChanged {
            if (EvaluationHelper.evaluateUsername(binding.usernameEditText.text.toString()) == null) {
                binding.usernameEditText.error = getString(R.string.wrong_username_format)
            }
        }

        binding.passwordEditText.doAfterTextChanged {
            if (EvaluationHelper.evaluatePassword(binding.passwordEditText.text.toString()) == null) {
                binding.passwordEditText.error = getString(R.string.wrong_password_format)
            }
        }

        binding.loginButton.setOnClickListener {
            if (EvaluationHelper.evaluateUsername(binding.usernameEditText.text.toString()) != null &&
                EvaluationHelper.evaluatePassword(binding.passwordEditText.text.toString()) != null
            )
                model.signInUser(
                    EvaluationHelper.evaluateUsername(binding.usernameEditText.text.toString())
                        ?: "",
                    EvaluationHelper.evaluatePassword(binding.passwordEditText.text.toString())
                        ?: ""
                )
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
