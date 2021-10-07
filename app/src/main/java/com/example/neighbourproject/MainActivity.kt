package com.example.neighbourproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.neighbourproject.databinding.ActivityMainBinding
<<<<<<< HEAD
import com.example.neighbourproject.ui.location.LocationActivity
=======
import com.example.neighbourproject.ui.SearchActivity
>>>>>>> c0347d26c15b704807156974fff9fa8d4d95a840

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        //TODO will be replaced with a login event
        startActivity(Intent(this, SearchActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        //TODO just for getting stuff started
        startActivity(Intent(this, LocationActivity::class.java))
    }
}