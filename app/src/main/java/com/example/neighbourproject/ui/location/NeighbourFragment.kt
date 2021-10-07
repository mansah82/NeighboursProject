package com.example.neighbourproject.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.databinding.NeighbourFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class NeighbourFragment : Fragment() {
    companion object {
        private const val TAG = "NeighbourFragment"
    }

    private lateinit var binding: NeighbourFragmentBinding

    private val model: NeighbourViewModel by activityViewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NeighbourFragmentBinding.inflate(inflater)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    binding.locationText.text = "Position Permission Granted"
                    getLocation()
                } else {
                    binding.locationText.text = "Position Permission NOT Granted"
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestLocationPermission()
    }

    private fun showPermissionRequestExplanation(
        permission: String,
        message: String,
        retry: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("$permission Required")
            setMessage(message)
            setPositiveButton("Ok") { _, _ -> retry?.invoke() }
            setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
        }.show()
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            binding.locationText.text = "Position Permission Already Granted"
            Log.d(TAG, "Permission granted")
            getLocation()
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //Ask for permission
                Log.d(TAG, "Ask for it and explain why")
                showPermissionRequestExplanation(
                    "ACCESS_COARSE_LOCATION",
                    "Do you want the permission so we can pin-point your activity",
                ) { requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION) }
            } else {
                Log.d(TAG, "Just request it")
                // Everything is fine you can simply request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                Log.d(TAG, "Fetched my last location lat: ${it.latitude} lon: ${it.longitude}")
            }
            .addOnFailureListener {
                Log.d(TAG, "Fetched my last location - Failed")
            }


    }
}