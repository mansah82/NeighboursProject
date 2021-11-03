package com.example.neighbourproject.ui.chat

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.text.Layout
import android.util.LayoutDirection
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import com.example.neighbourproject.R
import com.example.neighbourproject.chat.Chat
import com.example.neighbourproject.chat.data.ChatMessage

class ChatLogAdapter(val context: Context, val chat : List<ChatMessage>) :
        RecyclerView.Adapter<ChatLogAdapter.ViewHolder>() {
    val TAG = "ChatLogAdapter"
    val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${chat.size}")
        return chat.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLogAdapter.ViewHolder {
        val itemViewUser = layoutInflater.inflate(R.layout.chat_bubble_list_chat_log, parent, false)
        Log.d(TAG, "OncreateViewHolder: ")
        return ViewHolder(itemViewUser)
    }
    override fun onBindViewHolder(holder: ChatLogAdapter.ViewHolder, position: Int) {
        holder.chatBubbleUser.text = chat[position].message
        Log.d(TAG, "onBindViewHolder: ")
        if (chat[position].name == "You") {
            holder.chatBubbleUser.setBackgroundResource(R.drawable.rounded_edittext_signin)
            holder.chatBubbleUser.gravity = Gravity.END
        } else {
            holder.chatBubbleUser.setBackgroundResource(R.drawable.rounded_corners_darker_colors)
            holder.chatBubbleUser.gravity = Gravity.LEFT
        }
    }

    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatBubbleUser = itemView.findViewById<TextView>(R.id.chatBubble)
        //val constraintlayout = itemView.findViewById<ConstraintLayout>(R.id.constraintlayout)




    }
}





