package com.example.neighbourproject.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepositoryTest: UserService {
    private val registerStatusLive: MutableLiveData<RegisterStatus> =
        MutableLiveData(RegisterStatus(null, null))
    override val registerStatus: LiveData<RegisterStatus> = registerStatusLive

    private val loginStatusLive: MutableLiveData<LoginStatus> =
        MutableLiveData(LoginStatus(null, null))
    override val loginStatus: LiveData<LoginStatus> = loginStatusLive

    override suspend fun registerUser(username: String, password: String) {
        registerStatusLive.postValue( RegisterStatus("$username-$password", null))
    }

    override suspend fun loginUser(username: String, password: String) {
        loginStatusLive.postValue(LoginStatus("$username-$password", null))
    }

    override suspend fun logoutUser() {
        loginStatusLive.postValue(LoginStatus(null, null))
    }

    override fun isLoggedIn(): Boolean {
        loginStatusLive.value?.let{
            if(it.success != null)
                return true
        }
        return false
    }
}