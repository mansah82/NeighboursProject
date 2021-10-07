package com.example.neighbourproject.ui.location

import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.neighbourproject.databinding.LocationActivityBinding

class LocationActivity : AppCompatActivity() {

    private lateinit var binding: LocationActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}