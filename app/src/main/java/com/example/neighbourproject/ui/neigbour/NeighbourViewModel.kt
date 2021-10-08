package com.example.neighbourproject.ui.neigbour

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.Neighbour
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NeighbourViewModel : ViewModel(), KoinComponent {

    private val service: NeighboursService by inject()

    private var myLocation : Location? = null

    private var neighbour : Neighbour? = null

    fun setLocation(location : Location){
        myLocation = location
    }

    fun getLocation(): Location?{
        return myLocation
    }

    fun selectedNeighbour(id : String): Boolean{
        neighbour = service.getNeighbourById(id)
        Log.d("NeighbourViewModel", "Setting neighbour: $neighbour")
        return neighbour != null
    }

    fun getNeighbour(): Neighbour?{
        return neighbour
    }
}