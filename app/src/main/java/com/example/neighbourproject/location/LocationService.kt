package com.example.neighbourproject.location

import com.example.neighbourproject.neighbour.data.Position

interface LocationService {
    fun setLastPosition(position: Position)
    fun getLastPosition(): Position?
    fun calculateDistanceToMyPosition(position: Position): Double
}