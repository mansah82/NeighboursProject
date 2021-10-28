package com.example.neighbourproject.ui.edit

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.location.LocationService
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.*
import com.example.neighbourproject.storage.StorageService
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.ByteArrayOutputStream

class EditViewModel : ViewModel(), KoinComponent {
    companion object {
        private const val TAG = "EditViewModel"
    }

    private val neighbourService: NeighboursService by inject()
    private val locationService: LocationService by inject()
    private val storageService: StorageService by inject()

    fun getUserProfile(): People? {
        return neighbourService.userProfileUpdate.value
    }

    fun editUserProfile(profile: People) {
        Log.d(TAG, "Profile: $profile")
        viewModelScope.launch(Dispatchers.IO) {
            neighbourService.updateUserProfile(profile)
        }
    }

    fun getCurrentPosition(): Position? {
        return locationService.getLastPosition()
    }

    fun loadImage(context: Context, url: String, view: ImageView) {
        storageService.loadImage(context, url, view)
    }

    fun writeImage(bitmap: Bitmap): String {
        return storageService.writeImageStorage(neighbourService.getSignedInUid().plus(".jpeg"), bitmap)
    }
}