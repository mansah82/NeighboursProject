package com.example.neighbourproject.ui.neigbour

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.neighbourproject.location.LocationService
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.neighbour.data.Position
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NeighbourViewModel : ViewModel(), KoinComponent {
    companion object{
        private const val TAG = "NeighbourViewModel"
    }

    private val neighboursService: NeighboursService by inject()
    private val locationService: LocationService by inject()

    private var people : People? = null

    fun setLastPosition(position: Position){
        locationService.setLastPosition(position)
    }

    fun calculateDistanceToMe(position: Position): String{
        val dist : Double = locationService.calculateDistanceToMyPosition(position)/1000
        return if(dist < 0)
            "You have no position"
        else
            String.format("%.2f", dist).plus( " km")
    }

    fun selectedNeighbour(id : String): Boolean{
        people = neighboursService.getNeighbourById(id)
        Log.d("NeighbourViewModel", "Setting neighbour: $people")
        return people != null
    }

    fun getNeighbour(): People?{
        return people
    }
}