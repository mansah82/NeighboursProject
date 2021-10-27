package com.example.neighbourproject.neighbour

import androidx.lifecycle.LiveData
import com.example.neighbourproject.neighbour.data.FriendStatus
import com.example.neighbourproject.neighbour.data.People

interface NeighboursService {
    val userProfileUpdate : LiveData<People?>

    suspend fun signeIn(id : String)

    suspend fun updateUserProfile(profile : People)

    fun getNeighbourById(id: String): People?

    val searchResultUpdate: LiveData<List<People>>

    fun setSearch(searchParameters : SearchParameters)

    fun getFriends(): List<People>

    fun getFriendsStatus(): Map<String, FriendStatus>

    suspend fun setFriend(friendId: String)
}