package com.example.neighbourproject.ui.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val service: NeighboursService by inject()

    //TODO Remove when edit profile done
    private val myProfile = People(
        "Kalle 3",
        "Kallesson",
        Gender.MALE,
        58,
        mutableListOf(
            Interest("Name", AreaOfInterest("Location")),
            Interest("Name", AreaOfInterest("Location", "0", "0"))
        ),
        "url - to image",
        RelationshipStatus.SINGLE
    )

    fun editUserProfile(profile: People?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(profile == null) {
                service.updateUserProfile(myProfile)
                Log.d(TAG, "Writing crap profile to db")
            }else{
                service.updateUserProfile(profile)
                Log.d(TAG, "Writing profile to db")
            }
        }
    }
}