package com.example.neighbourproject.neighbour.data


data class Neighbour(
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val age: Int,
    val interests: List<Interest>,
    val image : String = ""
)
