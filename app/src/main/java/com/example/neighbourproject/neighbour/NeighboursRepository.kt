package com.example.neighbourproject.neighbour

import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.Interest
import com.example.neighbourproject.neighbour.data.Neighbour

//TODO this is a Firebase data mock
class NeighboursRepository : NeighboursService {
    companion object {
        private const val TAG = "NeighboursRepository"
    }

    private val neighbours = mutableListOf<Neighbour>()

    init {
        //TODO Adding some default test data, remove this
        neighbours.add(
            Neighbour(
                "Adam",
                "Adamsson",
                Gender.MALE,
                34,
                mutableListOf<Interest>(
                    Interest("Food", "Flen"),
                    Interest("Cars", "Flen") )
            )
        )
        neighbours.add(
            Neighbour(
                "Beata",
                "Beatasson",
                Gender.FEMALE,
                35,
                mutableListOf<Interest>(
                    Interest("Food", "Stockholm"),
                    Interest("Movies", "Stockholm") )
            )
        )
        neighbours.add(
            Neighbour(
                "Cea",
                "Ceasson",
                Gender.ENBY,
                36,
                mutableListOf<Interest>(
                    Interest("Dance", "Täby"),
                    Interest("Movies", "Stockholm") )
            )
        )
        neighbours.add(
            Neighbour(
                "Daniel",
                "Danielsson",
                Gender.NONE,
                37,
                mutableListOf<Interest>(
                    Interest("Dance", "Ludvika"),
                    Interest("Movies", "Ludvika") )
            )
        )
        neighbours.add(
            Neighbour(
                "Eva",
                "Evasson",
                Gender.FEMALE,
                38,
                mutableListOf<Interest>(
                    Interest("Dance", "Ludvika"),
                    Interest("Food", "Ludvika") )
            )
        )
        neighbours.add(
            Neighbour(
                "Frans",
                "Fransson",
                Gender.MALE,
                39,
                mutableListOf<Interest>(
                    Interest("Dance", "Ludvika"),
                    Interest("Food", "Ludvika"),
                    Interest("Go-cart", "Ludvika"),
                    Interest("Ninjas", "Ludvika")
                )
            )
        )
        neighbours.add(
            Neighbour(
                "Gunhild",
                "Gunhildsson",
                Gender.FEMALE,
                40,
                mutableListOf<Interest>(
                    Interest("Dance", "Avesta"),
                    Interest("Food", "Avesta"),
                    Interest("Go-cart", "Avesta"),
                    Interest("Ninjas", "Avesta")
                )
            )
        )
    }

    override fun getNeighboursByAge(minAge: Int, maxAge: Int): List<Neighbour>{
        val searchResult = mutableListOf<Neighbour>()
        for(neighbour in neighbours){
            if(neighbour.age in minAge..maxAge){
                searchResult.add(neighbour)
            }
        }
        return searchResult
    }

    override fun getNeighboursByGender(gender: Gender): List<Neighbour> {
        val searchResult = mutableListOf<Neighbour>()
        for (neighbour in neighbours) {
            if (neighbour.gender == gender) {
                searchResult.add(neighbour)
            }
        }
        return searchResult
    }

    override fun getNeighbourById(id: String): Neighbour? {
        for(neighbour in neighbours){
            if(neighbour.id == id){
                return neighbour
            }
        }
        return null
    }

}