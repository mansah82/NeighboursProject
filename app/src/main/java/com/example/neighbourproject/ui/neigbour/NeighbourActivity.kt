package com.example.neighbourproject.ui.neigbour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.neighbourproject.databinding.NeighbourActivityBinding

class NeighbourActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "NeighbourActivity"
    }

    private lateinit var binding: NeighbourActivityBinding

    private val model: NeighbourViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NeighbourActivityBinding.inflate(layoutInflater)

        var gotValidNeighbour = false
        intent.getStringExtra(ExtrasKey.KEY_USER_ID)?.let { id ->
            if (model.selectedNeighbour(id))
                gotValidNeighbour = true
        }
        if (!gotValidNeighbour)
            finish()

        setContentView(binding.root)
    }
}
