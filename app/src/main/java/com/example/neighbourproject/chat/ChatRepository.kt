package com.example.neighbourproject.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.neighbourproject.chat.data.ChatMessage
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class ChatRepository: ChatService {
    companion object {
        private const val TAG = "ChatRepository"
        private const val CHAT_COLLECTION = "chat"
    }

    private val messageList: MutableList<ChatMessage> = mutableListOf()

    private val messages: MutableLiveData<List<ChatMessage>> = MutableLiveData(messageList)

    private val db = Firebase.firestore

    override suspend fun startChat() {
        startListeningOnChat()
    }

    var query: ListenerRegistration? = null

    private fun startListeningOnChat() {
        val itemsRef = db.collection(CHAT_COLLECTION).orderBy("createdAt").limitToLast(40)
        query = itemsRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                messageList.clear()
                for (document in snapshot.documents) {
                    val item = document.toObject(ChatMessage::class.java)
                    if (item != null) {
                        messageList.add(item)
                    }
                }
                update(messageList)
            }
        }
    }

    override suspend fun stopChat() {
        query?.remove()
        query = null
    }

    override fun getLiveMessages(): LiveData<List<ChatMessage>>{
        return messages
    }

    override fun writeMessage(message: ChatMessage) {
        message.createdAt = Timestamp(Date())
        db.collection(CHAT_COLLECTION)
            .add(message)
    }

    private fun update(mess: List<ChatMessage>){
        messages.postValue(mess)
    }
}