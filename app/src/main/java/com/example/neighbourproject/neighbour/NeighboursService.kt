package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.Neighbour

interface NeighboursService {
    fun getNeighboursByAge(minAge: Int, maxAge: Int): List<Neighbour>
    fun getNeighboursByGender(gender: Gender): List<Neighbour>
}