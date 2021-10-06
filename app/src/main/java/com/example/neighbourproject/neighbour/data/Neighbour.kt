package com.example.neighbourproject.neighbour.data


data class Neighbour(
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val age: Int,
    private val interests: MutableList<Interest> = mutableListOf(),
    var image : String = ""
){

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

