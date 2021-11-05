package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import com.example.neighbourproject.neighbour.data.FriendStatus
import com.example.neighbourproject.neighbour.data.People

interface NeighboursService {
    val userProfileUpdate: LiveData<People?>

    suspend fun signeIn(id: String)

    fun getSignedInUid(): String

    suspend fun updateUserProfile(profile: People)

    fun getNeighbourById(id: String): People?

    val searchResultUpdate: LiveData<List<People>>

    fun setSearch(searchParameters: SearchParameters)

    fun getFriendsStatus(): Map<String, FriendStatus>

    suspend fun addFriend(friendId: String)

    suspend fun removeFriend(friendId: String)
}