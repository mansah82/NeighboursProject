package com.example.neighbourproject.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserRepository : UserService {
    companion object {
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
        if (username.isEmpty() || password.isEmpty()) {
            registerStatusLive.postValue(
                RegisterStatus(
                    null,
                    "Email and/or password may not be empty"
                )
            )

        } else {
            auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        registerStatusLive.postValue(RegisterStatus(username, null))
                    } else {
                        registerStatusLive.postValue(
                            RegisterStatus(
                                null,
                                "createUser:failure ".plus(username)
                            )
                        )
                    }
                }
        }
    }

    private var isLoggedIn = false

    override suspend fun loginUser(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            loginStatusLive.postValue(LoginStatus(null, "Email and/or password may not be empty"))

        } else {
            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        auth.currentUser?.let {
                            isLoggedIn = true
                            loginStatusLive.postValue(LoginStatus(it.uid, null))
                        }
                    } else {
                        loginStatusLive.postValue(
                            LoginStatus(
                                null,
                                "Email and/or password is incorrect"
                            )
                        )
                    }
                }
        }
    }

    override suspend fun logoutUser() {
        isLoggedIn = false
        auth.signOut()
    }

    override fun isLoggedIn(): Boolean {
        return (auth.currentUser != null) && isLoggedIn
    }
}