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
                        if(myProfileId != ""){
                            if(item.id == myProfileId){
                                neighbours.remove(item)
                            }
                        }
                    }
                }
            }
        }
    }
    private var myProfileId = ""

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
}