package com.example.neighbourproject.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.neighbourproject.R
import com.example.neighbourproject.databinding.ActivitySearchBinding
import com.example.neighbourproject.neighbour.data.Position
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class SearchActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SearchActivity"
    }

    private val model: SearchViewModel by viewModels()

    private lateinit var binding: ActivitySearchBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)

        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    Log.d(TAG, "Position Permission Granted")
                    getLocation()
                } else {
                    Log.d(TAG, "Position Permission NOT Granted")
                }
            }

    }


    override fun onStart() {
        super.onStart()
        requestLocationPermission()
    }

    private fun showPermissionRequestExplanation(
        permission: String,
        message: String,
        retry: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(this).apply {
            setTitle("$permission Required")
            setMessage(message)
            setPositiveButton(getString(R.string.location_dialog_ok)) { _, _ -> retry?.invoke() }
            setNegativeButton(getString(R.string.location_dialog_negative)) { dialog, _ ->
                dialog.cancel()
            }
        }.show()
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Permission already granted")
            getLocation()
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Log.d(TAG, "Ask for permission and explain why")
                showPermissionRequestExplanation(
                    getString(R.string.location_dialog_permission),
                    getString(R.string.location_motivation),
                ) { requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) }
            } else {
                Log.d(TAG, "Just request permission")
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        model.setLastPosition(Position(task.result.latitude, task.result.longitude))
                        Log.d(TAG, "Fetched my last location")
                    } else {
                        Log.d(TAG, "Fetched my last location - Failed on completed")
                    }
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Fetched my last location - Failed")
            }
    }
}