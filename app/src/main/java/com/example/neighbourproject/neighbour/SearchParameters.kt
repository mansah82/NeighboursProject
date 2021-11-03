package com.example.neighbourproject.neighbour

import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.RelationshipStatus

data class SearchParameters(
    val minAge: Int = DEFAULT_MIN_AGE,
    val maxAge: Int = DEFAULT_MAX_AGE,
    val genders: List<Gender> = listOf(),
    val relationshipStatuses: List<RelationshipStatus> = listOf(),
    val text: String = ""
) {
    companion object {
        const val DEFAULT_MIN_AGE = 0
        const val DEFAULT_MAX_AGE = 140
    }
}