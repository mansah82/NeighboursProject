package com.example.neighbourproject.ui.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.chat.Chat

class ChatLogAdapter(val context: Context, val chat : List<Chat>) :
        RecyclerView.Adapter<ChatLogAdapter.ViewHolder>() {
    val TAG = "ChatLogAdapter"
    val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ")
        return chat.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLogAdapter.ViewHolder {
        val itemViewUser = layoutInflater.inflate(R.layout.chat_bubble_list_chat_log, parent, false)
        val itemViewRecivier = layoutInflater.inflate(R.layout.chat_bubble_from_user, parent, false)

        Log.d(TAG, "OncreateViewHolder: ")
        return ViewHolder(itemViewUser)
    }

    override fun onBindViewHolder(holder: ChatLogAdapter.ViewHolder, position: Int) {
        val getChatMessage = chat
        Log.d(TAG, "onBindViewHolder: ")



    }

    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatBubbleUser = itemView.findViewById<TextView>(R.id.chatBubble)
        val chatBubbleReciever = itemView.findViewById<TextView>(R.id.chat_bubble_recieve)


    }
}





