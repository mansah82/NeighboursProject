package com.example.neighbourproject.ui.location

import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.neighbourproject.databinding.NeighbourActivityBinding

class NeighbourActivity : AppCompatActivity() {

    private lateinit var binding: NeighbourActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NeighbourActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}