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

    private val userProfileRemote: MutableLiveData<People?> = MutableLiveData<People?>(null)

    override val userProfileUpdate: LiveData<People?> = userProfileRemote

    private val neighbours = mutableListOf<People>()

    private var signedInUserUid = ""

    private val db = Firebase.firestore

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
                    }
                }
                doSearch()
            }
        }
    }

    override suspend fun signeIn(id: String) {
        val docRef = db.collection(PERSON_COLLECTION).document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                signedInUserUid = id

                startListeningForNeighbours()

                if (document.data != null) {
                    Log.d(TAG, "Data for profile: ${document.data}")
                    val person = document.toObject(People::class.java)
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
            userProfileRemote.postValue(profile)
            db.collection(PERSON_COLLECTION).document(signedInUserUid).set(profile)
        }
    }

    private val searchResultRemote : MutableLiveData<List<People>> =  MutableLiveData(listOf())
    override val searchResultUpdate: LiveData<List<People>> = searchResultRemote

    private var searchParameters: SearchParameters? = null
    override fun setSearch(searchParameters: SearchParameters) {
        this.searchParameters = searchParameters

        doSearch()
    }

    private fun doSearch(){
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