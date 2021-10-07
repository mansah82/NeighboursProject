package com.example.neighbourproject.neighbour

import com.example.neighbourproject.neighbour.data.Gender
import junit.framework.TestCase
import org.junit.Test

class NeighboursRepositoryTest : TestCase(){
    @Test
    fun testSearchByAge(){
        //TODO This is a bit strange since i Do not control in-data, this will be flaky
        val service : NeighboursService = NeighboursRepository()
        assertEquals(7, service.getNeighboursByAge(20, 40).size)
        assertEquals(1, service.getNeighboursByAge(35, 35).size)
    }

    @Test
    fun testSearchByGender(){
        //TODO This is a bit strange since i Do not control in-data, this will be flaky
        val service : NeighboursService = NeighboursRepository()
        assertEquals(3, service.getNeighboursByGender(Gender.FEMALE).size)
        assertEquals("Beata", service.getNeighboursByGender(Gender.FEMALE)[0].firstName)
    }
}