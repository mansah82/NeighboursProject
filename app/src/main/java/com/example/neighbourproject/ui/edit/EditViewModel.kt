package com.example.neighbourproject.ui.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.location.LocationService
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditViewModel: ViewModel(), KoinComponent {
    companion object{
        private const val TAG = "EditViewModel"
    }
    private val neighbourService: NeighboursService by inject()
    private val locationService: LocationService by inject()

    fun getUserProfile(): People?{
        return neighbourService.userProfileUpdate.value
    }

    fun editUserProfile(profile: People) {
        viewModelScope.launch(Dispatchers.IO) {
            neighbourService.updateUserProfile(profile)
        }
    }
}