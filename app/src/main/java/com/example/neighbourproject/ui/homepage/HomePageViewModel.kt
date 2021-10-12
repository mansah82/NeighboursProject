package com.example.neighbourproject.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomePageViewModel(): ViewModel(), KoinComponent{

    private val service: NeighboursService by inject()

    fun getUserProfileUpdate(): LiveData<People?>{
        return service.userProfileUpdate
    }

    fun isSignedIn(): Boolean{
        return service.isSignedIn()
    }

    fun setSignedInUser(uid: String){
        viewModelScope.launch(Dispatchers.IO) {
            service.signeIn(uid)
        }
    }

    fun signOut(){

    }
}