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

    //TODO Remove when edit profile done
    private val myProfile = People(
        "Kalle",
        "Kallesson",
        Gender.MALE,
        58,
        mutableListOf(
            Interest("Name", Area("Location")),
            Interest("Dance", Area("Täby", Position(59.2889,17.8888))),
            Interest("Name", Area("Location", Position(59.0,17.0)))
        ),
        "url - to image",
        RelationshipStatus.SINGLE
    )

    fun editUserProfile(profile: People?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(profile == null) {
                neighbourService.updateUserProfile(myProfile)
                Log.d(TAG, "Writing crap profile to db")
            }else{
                neighbourService.updateUserProfile(profile)
                Log.d(TAG, "Writing profile to db")
            }
        }
    }
}