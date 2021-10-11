package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.neighbour.data.*

class NeighboursRepositoryTest() : NeighboursService {
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
                mutableListOf<Interest>(
                    Interest("Food", AreaOfInterest("Flen")),
                    Interest("Cars", AreaOfInterest("Flen"))
                )
            )
        )
        neighbours.add(
            People(
                "Beata",
                "Beatasson",
                Gender.FEMALE,
                35,
                mutableListOf<Interest>(
                    Interest("Food", AreaOfInterest("Stockholm", "10", "31")),
                    Interest("Movies", AreaOfInterest("Stockholm"))
                )
            )
        )
        neighbours.add(
            People(
                "Cea",
                "Ceasson",
                Gender.ENBY,
                36,
                mutableListOf<Interest>(
                    Interest("Dance", AreaOfInterest("Täby")),
                    Interest("Movies", AreaOfInterest("Stockholm"))
                )
            )
        )
        neighbours.add(
            People(
                "Daniel",
                "Danielsson",
                Gender.NONE,
                37,
                mutableListOf<Interest>(
                    Interest("Dance", AreaOfInterest("Ludvika")),
                    Interest("Movies", AreaOfInterest("Ludvika"))
                ),
            )
        )
        neighbours.add(
            People(
                "Eva",
                "Evasson",
                Gender.FEMALE,
                38,
                mutableListOf<Interest>(
                    Interest("Dance", AreaOfInterest("Ludvika")),
                    Interest("Food", AreaOfInterest("Ludvika"))
                )
            )
        )
        neighbours.add(
            People(
                "Frans",
                "Fransson",
                Gender.MALE,
                39,
                mutableListOf<Interest>(
                    Interest("Dance", AreaOfInterest("Ludvika")),
                    Interest("Food", AreaOfInterest("Ludvika")),
                    Interest("Go-cart", AreaOfInterest("Ludvika")),
                    Interest("Ninjas", AreaOfInterest("Ludvika"))
                )
            )
        )
        neighbours.add(
            People(
                "Gunhild",
                "Gunhildsson",
                Gender.FEMALE,
                40,
                mutableListOf<Interest>(
                    Interest("Dance", AreaOfInterest("Avesta")),
                    Interest("Food", AreaOfInterest("Avesta")),
                    Interest("Go-cart", AreaOfInterest("Avesta")),
                    Interest("Ninjas", AreaOfInterest("Avesta"))
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
            Interest("Name", AreaOfInterest("Location")),
            Interest("Name", AreaOfInterest("Location", "0", "0"))
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

    override fun signOut() {
        userProfileRemote.value = null
    }

    override fun isSignedIn(): Boolean {
        return userProfileRemote.value != null
    }

}