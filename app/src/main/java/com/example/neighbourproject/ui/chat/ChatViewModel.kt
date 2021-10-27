package com.example.neighbourproject.ui.chat

import androidx.lifecycle.ViewModel
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatViewModel: ViewModel(), KoinComponent {
    private val neighbourService: NeighboursService by inject()
    fun getFriends(): List<People>{
        return neighbourService.getFriends()
    }

}