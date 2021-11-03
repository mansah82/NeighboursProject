package com.example.neighbourproject.neighbour

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.neighbour.data.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NeighboursRepository : NeighboursService {
    companion object {
        private const val TAG = "NeighboursRepository"

        private const val PERSON_COLLECTION = "neighbours"
    }

    private val searchResultRemote: MutableLiveData<List<People>> = MutableLiveData(listOf())

    override val searchResultUpdate: LiveData<List<People>> = searchResultRemote

    private val userProfileRemote: MutableLiveData<People?> = MutableLiveData<People?>(null)

    override val userProfileUpdate: LiveData<People?> = userProfileRemote

    private val neighbours = mutableListOf<People>()

    private var signedInUserUid = ""

    private val db = Firebase.firestore

    private val friendStatuses: MutableMap<String, FriendStatus> = mutableMapOf()

    override fun getFriendsStatus(): Map<String, FriendStatus> {
        return friendStatuses
    }

    override suspend fun addFriend(friendId: String) {
        userProfileRemote.value?.let {
            if (!it.friends.contains(friendId))
                it.friends.add(friendId)
            updateUserProfile(it)
        }
    }

    override suspend fun removeFriend(friendId: String) {
        userProfileRemote.value?.let {
            if (it.friends.contains(friendId))
                it.friends.remove(friendId)
            updateUserProfile(it)
        }
    }

    override fun getNeighbourById(id: String): People? {
        for (neighbour in neighbours) {
            if (neighbour.id == id) {
                return neighbour
            }
        }
        return null
    }

    private fun startListeningForNeighbours() {
        val itemsRef = db.collection(PERSON_COLLECTION)
        itemsRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                neighbours.clear()

                for (document in snapshot.documents) {
                    val item = document.toObject(People::class.java)
                    if (item != null) {
                        neighbours.add(item)
                        //Remove my profile from this list
                        if (myProfileId != "") {
                            if (item.id == myProfileId) {
                                neighbours.remove(item)
                            }
                        }
                    }
                }
                updateFriendsMap()
                doSearch()
            }
        }
    }

    private var myProfileId = ""

   // private fun updateFriendsList(){}

    override suspend fun signeIn(id: String) {
        val docRef = db.collection(PERSON_COLLECTION).document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                signedInUserUid = id
                myProfileId = ""

                startListeningForNeighbours()

                if (document.data != null) {
                    Log.d(TAG, "Data for profile: ${document.data}")
                    val person = document.toObject(People::class.java)
                    person?.let {
                        myProfileId = it.id
                    }
                    userProfileRemote.postValue(person)

                } else {
                    Log.d(TAG, "No such document")
                    userProfileRemote.postValue(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    override suspend fun updateUserProfile(profile: People) {
        if (signedInUserUid != "") {
            myProfileId = profile.id
            userProfileRemote.postValue(profile)
            db.collection(PERSON_COLLECTION).document(signedInUserUid).set(profile)
        }
    }

    private var searchParameters: SearchParameters? = null
    override fun setSearch(searchParameters: SearchParameters) {
        this.searchParameters = searchParameters

        doSearch()
    }

    override fun getFriends(): List<People> {
        TODO("Not yet implemented")
    }

    private fun updateFriendsMap() {
        userProfileRemote.value?.let {
            for (neighbour in neighbours) {
                var requested = false
                var askedFor = false
                //if(neighbour) set the stuff
                if (neighbour.friends.contains(myProfileId))
                    requested = true
                if (it.friends.contains(neighbour.id))
                    askedFor = true

                //Update the friends map
                if (!requested && !askedFor) {
                    friendStatuses[neighbour.id] = FriendStatus.NONE
                } else if (!requested && askedFor) {
                    friendStatuses[neighbour.id] = FriendStatus.PENDING
                } else if (requested && !askedFor) {
                    friendStatuses[neighbour.id] = FriendStatus.REQUESTED
                } else if (requested && askedFor) {
                    friendStatuses[neighbour.id] = FriendStatus.FRIENDS
                }
            }
        }
    }

    private fun doSearch() {
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
}