package com.example.neighbourproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.neighbourproject.databinding.ActivityMainBinding
import com.example.neighbourproject.ui.location.LocationActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hej
    }

    override fun onStart() {
        super.onStart()
        //TODO just for getting stuff started
        startActivity(Intent(this, LocationActivity::class.java))
    }
}