package com.example.neighbourproject.chat.data

import com.google.firebase.Timestamp
import java.util.*

data class ChatMessage(
    val id: String="",
    val name: String ="",
    val message: String="",
    var createdAt: Timestamp = Timestamp(Date()))
