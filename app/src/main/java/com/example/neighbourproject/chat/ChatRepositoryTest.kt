package com.example.neighbourproject.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.chat.data.ChatMessage
import kotlinx.coroutines.channels.consumesAll

class ChatRepositoryTest: ChatService {
    companion object{
        private var counter = 0;
    }

    private val messageList: MutableList<ChatMessage> = mutableListOf(ChatMessage("jei","Torsten", "Tjena"))

    override fun writeMessage(message: ChatMessage) {
        messageList.add(message)
        //Add a fake friend message also
        messageList.add(ChatMessage("jei","Friend", "Hello again - $counter"))
        messages.value = messageList
        counter++
    }

    private val messages: MutableLiveData<List<ChatMessage>> = MutableLiveData(messageList)

    override fun getLiveMessages(): LiveData<List<ChatMessage>>{
        return messages
    }

    override suspend fun startChat() {}
    override suspend fun stopChat() {}
}