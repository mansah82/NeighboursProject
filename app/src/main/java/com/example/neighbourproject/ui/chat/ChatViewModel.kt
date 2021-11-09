package com.example.neighbourproject.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourproject.chat.ChatService
import com.example.neighbourproject.chat.data.ChatMessage
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatViewModel: ViewModel(), KoinComponent {
    private val chatService: ChatService by inject()
    private val neighboursService: NeighboursService by inject()

    fun writeMessage(message: String){
        neighboursService.userProfileUpdate.value?.let {
            chatService.writeMessage(ChatMessage(it.id, it.firstName, message))
        }
    }
    fun getId(): String{
        return neighboursService.userProfileUpdate.value?.id?: ""
    }

    fun getLiveMessages(): LiveData<List<ChatMessage>>{
        return chatService.getLiveMessages()
    }

    fun startChat() {
        viewModelScope.launch(Dispatchers.IO) {
            chatService.startChat()
        }
    }
    fun stopChat() {
        viewModelScope.launch(Dispatchers.IO) {
            chatService.stopChat()
        }
    }
}