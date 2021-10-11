package com.example.neighbourproject.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomePageViewModel(): ViewModel(), KoinComponent{

    private val service: NeighboursService by inject()

    var signedIn = false

    fun getUserProfileUpdate(): LiveData<People?>{
        return service.userProfileUpdate
    }

    fun setSignedInUser(uid: String){
        signedIn = true
        viewModelScope.launch(Dispatchers.IO) {
            service.signedInAsUser(uid)
        }
    }

    fun signOut(){
        signedIn = false
    }
}