package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.neighbour.data.Position

interface NeighboursService {
    val userProfileUpdate : LiveData<People?>

    suspend fun signeIn(id : String)

    suspend fun updateUserProfile(profile : People)

    fun getNeighbourById(id: String): People?

    val searchResultUpdate: LiveData<List<People>>

    fun setSearch(searchParameters : SearchParameters)
}