package com.example.neighbourproject.neighbour.data

import junit.framework.TestCase
import org.junit.Test

class PeopleTest : TestCase() {
    @Test
    fun testAddInterest() {
        val neighbour = People("Kalle", "Kallesson", Gender.MALE, 58)
        assertEquals(0, neighbour.interests.size)
        neighbour.addInterest(Interest("Name", AreaOfInterest("Location")))
        assertEquals(1, neighbour.interests.size)
    }

    @Test
    fun testRemoveInterest() {
        val neighbour = People(
            "Kalle",
            "Kallesson",
            Gender.MALE,
            58,
            mutableListOf(Interest("Name", AreaOfInterest("Location")))
        )

        assertEquals(1, neighbour.interests.size)
        neighbour.removeInterest(neighbour.interests[0])
        assertEquals(0, neighbour.interests.size)
    }

    @Test
    fun testGetInterests() {
        val neighbour = People(
            "Kalle",
            "Kallesson",
            Gender.MALE,
            58,
            mutableListOf(
                Interest("Name", AreaOfInterest("Location")),
                Interest("Name", AreaOfInterest("Location"))
            )
        )

        assertEquals(2, neighbour.interests.size)
    }
}