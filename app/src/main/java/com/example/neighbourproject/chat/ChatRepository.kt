package com.example.neighbourproject.chat

import android.util.Log
import com.example.neighbourproject.chat.data.ChatMessage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatRepository: ChatService {

    override fun writeMessage(message: ChatMessage) {
        TODO("Not yet implemented")

    }

    override fun getMessages(): List<ChatMessage> {
       TODO("Not yet implemented")
       }

}