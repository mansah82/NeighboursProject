package com.example.neighbourproject.user

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.ui.homepage.HomePageActivity
import com.example.neighbourproject.ui.signup.SignUpActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository: UserService {
    companion object{
        private const val TAG = "UserRepository"
    }

    private val auth = Firebase.auth

    private val registerStatusLive: MutableLiveData<RegisterStatus> =
        MutableLiveData(RegisterStatus(null, null))
    override val registerStatus: LiveData<RegisterStatus> = registerStatusLive

    private val loginStatusLive: MutableLiveData<LoginStatus> =
        MutableLiveData(LoginStatus(null, null))
    override val loginStatus: LiveData<LoginStatus> = loginStatusLive

    override suspend fun registerUser(username: String, password: String) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    registerStatusLive.postValue(RegisterStatus(username, null))
                } else {
                    registerStatusLive.postValue(RegisterStatus(null, "createUser:failure ".plus(username)))
                }
            }
    }

    override suspend fun loginUser(username: String, password: String) {
        auth.signInWithEmailAndPassword( username, password )
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    auth.currentUser?.let {
                        loginStatusLive.postValue(LoginStatus(it.uid, null))
                    }
                } else {
                    loginStatusLive.postValue(LoginStatus(null, "Email and/or password is incorrect"))
                }
            }
    }

    override suspend fun logoutUser() {
        auth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}