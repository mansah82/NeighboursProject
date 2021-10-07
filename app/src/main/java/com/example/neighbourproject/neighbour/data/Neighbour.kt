package com.example.neighbourproject.neighbour.data

import java.util.*

data class Neighbour(
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val age: Int,
    private val interests: MutableList<Interest> = mutableListOf(),
    var image : String = ""
){
    val id : String = UUID.randomUUID().toString()

    fun addInterest(interest: Interest){
        interests.add(interest)
    }

    fun removeInterest(interest: Interest){
        interests.remove(interest)
    }

    fun getInterests(): List<Interest>{
        return interests
    }
}

