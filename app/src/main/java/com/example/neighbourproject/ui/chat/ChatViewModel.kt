package com.example.neighbourproject.ui.chat

import androidx.lifecycle.ViewModel
import com.example.neighbourproject.chat.ChatService
import com.example.neighbourproject.chat.data.ChatMessage
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatViewModel: ViewModel(), KoinComponent {
    private val neighbourService: NeighboursService by inject()
    private val chatService: ChatService by inject()
    fun getFriends(): List<People>{
        return neighbourService.getFriends()
    }
    fun writeMessage(message: ChatMessage){
        chatService.writeMessage(message)
    }
    fun getMessage(): List<ChatMessage>{
        return chatService.getMessages()
    }
}