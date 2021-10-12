package com.example.neighbourproject.location

import android.location.Location
import com.example.neighbourproject.neighbour.data.Position

class LocationRepository: LocationService {
    private var myPosition : Position? = null

    override fun setLastPosition(position: Position){
        myPosition = position
    }
    override fun getLastPosition(): Position?{
        return myPosition
    }

    override fun calculateDistanceToMyPosition(position: Position): Double{
        myPosition?.let {
            val results = FloatArray(1)
            Location.distanceBetween(
                it.latitude, it.longitude, position.latitude, position.longitude, results)
            return results[0].toDouble()
        }?: return -1.0
    }
}