package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.People

interface NeighboursService {
    fun getNeighboursByAge(minAge: Int, maxAge: Int): List<People>

    fun getNeighboursByGender(gender: Gender): List<People>

    fun getNeighbourById(id: String): People?

    val userProfileUpdate : LiveData<People?>

    fun isSignedIn(): Boolean

    suspend fun signeIn(id : String)

    fun signOut()

    suspend fun updateUserProfile(profile : People)
}