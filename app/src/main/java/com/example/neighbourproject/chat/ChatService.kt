package com.example.neighbourproject.chat

import com.example.neighbourproject.chat.data.ChatMessage

interface ChatService {
    fun writeMessage(message: ChatMessage)
    fun getMessages(): List<ChatMessage>
}