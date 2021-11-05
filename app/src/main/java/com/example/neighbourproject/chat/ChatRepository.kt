package com.example.neighbourproject.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.chat.data.ChatMessage

class ChatRepository: ChatService {
    companion object {
        private const val TAG = "ChatRepository"

        private const val CHAT_COLLECTION = "chat"
    }


    private val messageList: MutableList<ChatMessage> = mutableListOf(ChatMessage("jei","Torsten", "Tjena"))

    override fun writeMessage(message: ChatMessage) {
       //Write message to db

    }

    private fun update(mess: List<ChatMessage>){
        //Anropas från snapshotlisteners
        messages.postValue(mess)
    }
    private val messages: MutableLiveData<List<ChatMessage>> = MutableLiveData(messageList)

    override fun getLiveMessages(): LiveData<List<ChatMessage>>{
        return messages
    }
}