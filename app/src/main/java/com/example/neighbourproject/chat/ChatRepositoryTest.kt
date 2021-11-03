package com.example.neighbourproject.chat

import com.example.neighbourproject.chat.data.ChatMessage
import kotlinx.coroutines.channels.consumesAll

class ChatRepositoryTest: ChatService {
    companion object{
        private var counter = 0;
    }

    private val messageList: MutableList<ChatMessage> = mutableListOf(ChatMessage("Tony","Hello"))

    override fun writeMessage(message: ChatMessage) {
        messageList.add(message)
        //Add a fake friend message also
        messageList.add(ChatMessage("Friend", "Hello again - $counter"))
        counter++
    }

    override fun getMessages(): List<ChatMessage> {
        return messageList
    }

    //override fun updateMessage(){}
}