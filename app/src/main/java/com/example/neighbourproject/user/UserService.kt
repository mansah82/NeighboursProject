package com.example.neighbourproject.user

import androidx.lifecycle.LiveData

interface UserService {
    val registerStatus: LiveData<RegisterStatus>
    val loginStatus: LiveData<LoginStatus>

    suspend fun registerUser(username: String, password: String)
    suspend fun loginUser(username: String, password: String)
    suspend fun logoutUser()
    fun isLoggedIn(): Boolean
}