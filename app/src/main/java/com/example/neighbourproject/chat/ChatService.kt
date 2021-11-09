package com.example.neighbourproject.chat

import androidx.lifecycle.LiveData
import com.example.neighbourproject.chat.data.ChatMessage

interface ChatService {
    fun writeMessage(message: ChatMessage)
    fun getLiveMessages(): LiveData<List<ChatMessage>>

    suspend fun startChat()
    suspend fun stopChat()
}