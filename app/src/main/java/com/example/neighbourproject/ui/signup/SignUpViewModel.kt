package com.example.neighbourproject.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.user.RegisterStatus
import com.example.neighbourproject.user.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpViewModel(): ViewModel(), KoinComponent{

    private val userService: UserService by inject()

    fun getUserRegisterUpdate(): LiveData<RegisterStatus>{
        return userService.registerStatus
    }

    fun resisterUser(username: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            userService.registerUser(username, password)
        }
    }
}