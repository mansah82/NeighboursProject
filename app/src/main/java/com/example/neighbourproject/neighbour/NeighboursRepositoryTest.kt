package com.example.neighbourproject.neighbour

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.neighbour.data.*

class NeighboursRepositoryTest : NeighboursService {
    companion object {
        private const val TAG = "NeighboursRepositoryTest"
    }

    private val userProfileRemote: MutableLiveData<People?> = MutableLiveData<People?>(null)

    override val userProfileUpdate: LiveData<People?> = userProfileRemote

    private val searchResultRemote: MutableLiveData<List<People>> = MutableLiveData(listOf())

    override val searchResultUpdate: LiveData<List<People>> = searchResultRemote

    private var searchParameters: SearchParameters? = null

    private val friendStatuses: MutableMap<String, FriendStatus> = mutableMapOf()

    override fun getFriendsStatus(): Map<String, FriendStatus> {
        return friendStatuses
    }

    override suspend fun setFriend(friendId: String) {
        if (!myProfile.friends.contains(friendId)) {
            Log.d(TAG, "Adding Friend:  $friendId")
            myProfile.friends.add(friendId)
        }
    }

    override fun setSearch(searchParameters: SearchParameters) {
        this.searchParameters = searchParameters

        doSearch()
    }

    private fun updateFriendsMap() {
        for (neighbour in neighbours) {
            var requested: Boolean = false
            var askedFor: Boolean = false
            //if(neighbour) set the stuff
            if (neighbour.friends.contains(myProfile.id))
                requested = true
            if (myProfile.friends.contains(neighbour.id))
                askedFor = true

            //Update the friends map
            if (!requested && !askedFor) {
                friendStatuses[neighbour.id] = FriendStatus.NONE
            } else if (!requested && askedFor) {
                friendStatuses[neighbour.id] = FriendStatus.PENDING
            } else if (requested && !askedFor) {
                friendStatuses[neighbour.id] = FriendStatus.REQUEST
            } else if (requested && askedFor) {
                friendStatuses[neighbour.id] = FriendStatus.FRIENDS
            }

        }
    }

    private fun doSearch() {
        updateFriendsMap()

        searchParameters?.let { params ->
            val searchResult = mutableListOf<People>()
            val removeResult = mutableListOf<People>()

            // Get by age
            for (neighbour in neighbours) {
                if (neighbour.age in params.minAge..params.maxAge) {
                    searchResult.add(neighbour)
                }
            }

            // Get by gender
            for (neighbour in searchResult) {
                if (neighbour.gender !in params.genders) {
                    removeResult.add(neighbour)
                }
            }
            searchResult.removeAll(removeResult)
            removeResult.clear()

            // Get by relationship status
            for (neighbour in searchResult) {
                if (neighbour.relationshipStatus !in params.relationshipStatuses) {
                    removeResult.add(neighbour)
                }
            }
            searchResult.removeAll(removeResult)
            removeResult.clear()

            // Get by free search
            if (params.text != "") {
                for (neighbour in searchResult) {
                    if (!neighbour.toString().contains(params.text, true)) {
                        removeResult.add(neighbour)
                    }
                }
                searchResult.removeAll(removeResult)
                removeResult.clear()
            }

            searchResultRemote.value = searchResult
        }
    }

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
                    Interest("Food", Area("Stockholm", Position(59.0, 12.0))),
                    Interest("Movies", Area("Stockholm", null))
                ),
                "", RelationshipStatus.SINGLE
            )
        )
        neighbours.add(
            People(
                "Cea",
                "Ceasson",
                Gender.ENBY,
                36,
                mutableListOf(
                    Interest("Dance", Area("Täby", Position(59.2889, 17.8888))),
                    Interest("Movies")
                ),
                friends = mutableListOf("Yroll"),
                id = "Cea"

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
                friends = mutableListOf("Yroll"),
                id = "Daniel"

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
            Interest("Tennis", Area("Location")),
            Interest("Dance", Area("Täby", Position(59.2889, 17.8888))),
            Interest("Dance", Area("Ludvika")),
            Interest("Movies", Area("Ludvika")),
            Interest("Go-cart", Area("Avesta")),
            Interest("Ninjas", Area("Avesta"))

        ),
        "url - to image",
        RelationshipStatus.SINGLE,
        id = "Yroll",
        friends = mutableListOf("Cea"),
    )

    override suspend fun signeIn(id: String) {
        userProfileRemote.postValue(myProfile)
        //userProfileRemote.postValue(null)
    }

    override suspend fun updateUserProfile(profile: People) {
        myProfile = profile
        userProfileRemote.postValue(myProfile)
    }
}