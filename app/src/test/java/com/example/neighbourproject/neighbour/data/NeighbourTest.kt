package com.example.neighbourproject.neighbour.data

import junit.framework.TestCase
import org.junit.Test

class NeighbourTest : TestCase(){
    @Test
    fun testAddInterest(){
        val neighbour = Neighbour("Kalle", "Kallesson", Gender.MALE, 58)
        assertEquals(0, neighbour.getInterests().size)
        neighbour.addInterest(Interest("Name", "Location"))
        assertEquals(1, neighbour.getInterests().size)
    }

    @Test
    fun testRemoveInterest(){
        val neighbour = Neighbour(
            "Kalle",
            "Kallesson",
            Gender.MALE,
            58,
            mutableListOf(Interest("Name", "Location")))

        assertEquals(1, neighbour.getInterests().size)
        neighbour.removeInterest(neighbour.getInterests()[0])
        assertEquals(0, neighbour.getInterests().size)
    }

    @Test
    fun testGetInterests(){
        val neighbour = Neighbour(
            "Kalle",
            "Kallesson",
            Gender.MALE,
            58,
            mutableListOf(
                Interest("Name", "Location"),
                Interest("Name", "Location") ) )

        assertEquals(2, neighbour.getInterests().size)
    }
}