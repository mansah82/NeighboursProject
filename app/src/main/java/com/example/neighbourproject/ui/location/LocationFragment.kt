package com.example.neighbourproject.ui.location

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
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
import com.example.neighbourproject.databinding.LocationFragmentBinding

class LocationFragment : Fragment() {
    companion object{
        private const val TAG = "LocationFragment"
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private lateinit var binding: LocationFragmentBinding
    private val model: LocationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationFragmentBinding.inflate(inflater)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    binding.locationText.text = "Position Permission Granted"
                    val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE)
                            as LocationManager


                } else {
                    binding.locationText.text = "Position Permission NOT Granted"
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonReqLocation.setOnClickListener {
            requestStoragePermission()
        }
    }

    private fun showPermissionRequestExplanation(
        permission: String,
        message: String,
        retry: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("$permission Required")
            setMessage(message)
            setPositiveButton("Ok") { _, _ -> retry?.invoke()}
            setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
        }.show()
    }

    private fun requestStoragePermission() {
        if(ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "Permission granted")
        }else{
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                //Ask for permission
                Log.d(TAG, "Ask for it")
                showPermissionRequestExplanation(
                    "ACCESS_COARSE_LOCATION",
                    "Do you want the permission so we can pin-point your activity",
                ) { requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION) }
            }else{
                Log.d(TAG, "Just grab it")
                // Everything is fine you can simply request the permission
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            }


        }
    }
}