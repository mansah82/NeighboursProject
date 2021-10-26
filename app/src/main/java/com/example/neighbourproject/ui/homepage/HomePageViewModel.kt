package com.example.neighbourproject.ui.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.user.LoginStatus
import com.example.neighbourproject.user.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomePageViewModel : ViewModel(), KoinComponent {
    companion object {
        private const val TAG = "HomePageViewModel"
    }

    private val neighboursService: NeighboursService by inject()
    private val userService: UserService by inject()

    fun getUserProfileUpdate(): LiveData<People?> {
        return neighboursService.userProfileUpdate
    }

    fun getUserLoginUpdate(): LiveData<LoginStatus> {
        return userService.loginStatus
    }

    fun isSignedIn(): Boolean {
        return userService.isLoggedIn()
    }

    fun setSignedInUser(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            neighboursService.signeIn(uid)
        }
    }

    fun signInUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userService.loginUser(username, password)
        }
    }

    fun signOut() {
        Log.d(TAG, "Signing out")
        viewModelScope.launch(Dispatchers.IO) {
            userService.logoutUser()
        }
    }

    override fun onCleared() {
        signOut()
        super.onCleared()
    }
}