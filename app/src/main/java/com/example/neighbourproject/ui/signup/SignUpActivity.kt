package com.example.neighbourproject.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.ActivitySignUpBinding
import com.example.neighbourproject.ui.homepage.HomePageActivity
import com.example.neighbourproject.ui.neigbour.NeighbourActivity
import com.example.neighbourproject.user.EvaluationHelper
import com.example.neighbourproject.user.ExtrasKey
import com.example.neighbourproject.user.RegisterStatus

class SignUpActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SignUpActivity"
    }

    private val model: SignUpViewModel by viewModels()

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userResisterObserver = Observer<RegisterStatus> { status ->
            if (status.success != null) {
                val intent = Intent(this, HomePageActivity::class.java).also {
                    it.putExtra(
                        ExtrasKey.KEY_USER_NAME,
                        EvaluationHelper.evaluateUsername(binding.editTextEmailAddress.text.toString())
                    )
                    it.putExtra(
                        ExtrasKey.KEY_PASSWORD,
                        EvaluationHelper.evaluatePassword(binding.editTextPassword.text.toString())
                    )
                }
                startActivity(intent)
                finish()
            } else {
                if (status.failed != null) {
                    Toast.makeText(
                        baseContext, "Failed: ${status.failed}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        model.getUserRegisterUpdate().observe(this@SignUpActivity, userResisterObserver)

        binding.editTextEmailAddress.doAfterTextChanged {
            if (EvaluationHelper.evaluateUsername(binding.editTextEmailAddress.text.toString()) == null) {
                binding.editTextEmailAddress.error = getString(R.string.wrong_username_format)
            }
        }

        binding.editTextPassword.doAfterTextChanged {
            if (EvaluationHelper.evaluatePassword(binding.editTextPassword.text.toString()) == null) {
                binding.editTextPassword.error = getString(R.string.wrong_password_format)
            }
        }

        binding.signUpButton.setOnClickListener {
            if (EvaluationHelper.evaluateUsername(binding.editTextEmailAddress.text.toString()) != null &&
                EvaluationHelper.evaluatePassword(binding.editTextPassword.text.toString()) != null
            )
                model.resisterUser(
                    EvaluationHelper.evaluateUsername(binding.editTextEmailAddress.text.toString())
                        ?: "",
                    EvaluationHelper.evaluatePassword(binding.editTextPassword.text.toString())
                        ?: ""
                )
        }
    }
}