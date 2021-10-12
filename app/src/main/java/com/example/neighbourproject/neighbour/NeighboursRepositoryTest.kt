package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.neighbour.data.*

class NeighboursRepositoryTest: NeighboursService {
    companion object {
        private const val TAG = "NeighboursRepositoryTest"
    }

    private val userProfileRemote : MutableLiveData<People?> = MutableLiveData<People?>(null)
    override val userProfileUpdate: LiveData<People?> = userProfileRemote


    private val neighbours = mutableListOf<People>()
    init {
        neighbours.add(
            People(
                "Adam",
                "Adamsson",
                Gender.MALE,
                34,
                mutableListOf(
                    Interest("Food", Area("Flen")),
                    Interest("Cars")
                )
            )
        )
        neighbours.add(
            People(
                "Beata",
                "Beatasson",
                Gender.FEMALE,
                35,
                mutableListOf(
                    Interest("Food", Area("Stockholm", Position(59.0,12.0))),
                    Interest("Movies", Area("Stockholm", null))
                ),
                "",RelationshipStatus.SINGLE
            )
        )
        neighbours.add(
            People(
                "Cea",
                "Ceasson",
                Gender.ENBY,
                36,
                mutableListOf(
                    Interest("Dance", Area("Täby", Position(59.2889,17.8888))),
                    Interest("Movies")
                )
            )
        )
        neighbours.add(
            People(
                "Daniel",
                "Danielsson",
                Gender.NONE,
                37,
                mutableListOf(
                    Interest("Dance", Area("Ludvika")),
                    Interest("Movies", Area("Ludvika"))
                ),
            )
        )
        neighbours.add(
            People(
                "Eva",
                "Evasson",
                Gender.FEMALE,
                38,
                mutableListOf(
                    Interest("Dance", Area("Ludvika")),
                    Interest("Food", Area("Ludvika"))
                )
            )
        )
        neighbours.add(
            People(
                "Frans",
                "Fransson",
                Gender.MALE,
                39,
                mutableListOf(
                    Interest("Dance", Area("Ludvika")),
                    Interest("Food", Area("Ludvika")),
                    Interest("Go-cart", Area("Ludvika")),
                    Interest("Ninjas", Area("Ludvika"))
                )
            )
        )
        neighbours.add(
            People(
                "Gunhild",
                "Gunhildsson",
                Gender.FEMALE,
                40,
                mutableListOf(
                    Interest("Dance", Area("Avesta")),
                    Interest("Food", Area("Avesta")),
                    Interest("Go-cart", Area("Avesta")),
                    Interest("Ninjas", Area("Avesta"))
                )
            )
        )
    }

    override fun getNeighboursByAge(minAge: Int, maxAge: Int): List<People> {
        val searchResult = mutableListOf<People>()
        for (neighbour in neighbours) {
            if (neighbour.age in minAge..maxAge) {
                searchResult.add(neighbour)
            }
        }
        return searchResult
    }

    override fun getNeighboursByGender(gender: Gender): List<People> {
        val searchResult = mutableListOf<People>()
        for (neighbour in neighbours) {
            if (neighbour.gender == gender) {
                searchResult.add(neighbour)
            }
        }
        return searchResult
    }

    override fun getNeighbourById(id: String): People? {
        for (neighbour in neighbours) {
            if (neighbour.id == id) {
                return neighbour
            }
        }
        return null
    }

    private var myProfile = People(
        "Yroll",
        "Yrollsson",
        Gender.MALE,
        58,
        mutableListOf(
            Interest("Name", Area("Location"))
        ),
        "url - to image",
        RelationshipStatus.SINGLE
    )

    override suspend fun signeIn(id: String){
        userProfileRemote.postValue(myProfile)
        //userProfileRemote.postValue(null)
    }

    override suspend fun updateUserProfile(profile: People) {
        myProfile = profile
        userProfileRemote.postValue(myProfile)
    }
}