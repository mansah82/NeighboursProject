package com.example.neighbourproject.ui.chat

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.chat.data.ChatMessage
import android.widget.LinearLayout

class ChatLogAdapter(
    private val model: ChatViewModel,
    lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ChatLogAdapter.ViewHolder>() {
    companion object {
        private const val TAG = "ChatLogAdapter"
    }

    private val chatObserver = Observer<List<ChatMessage>> {
        notifyDataSetChanged()
    }

    init {
        model.getLiveMessages().observe(lifecycleOwner, chatObserver)
    }

    override fun getItemCount(): Int {
        return model.getLiveMessages().value?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLogAdapter.ViewHolder {
        val itemViewUser = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_bubble_list_chat_log, parent, false)
        return ViewHolder(itemViewUser)
    }

    override fun onBindViewHolder(holder: ChatLogAdapter.ViewHolder, position: Int) {
        model.getLiveMessages().value?.let {
            var data = ""
            Log.d(TAG, "onBindViewHolder: ")
            if (it[position].id == model.getId()) {
                holder.chatBubbleUser.setBackgroundResource(R.drawable.rounded_edittext_signin)

                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.gravity = Gravity.END
                holder.chatBubbleUser.layoutParams = params
                holder.usernameOverBubble.gravity = Gravity.RIGHT
                holder.usernameOverBubble.text = "Me"
            } else {
                holder.chatBubbleUser.setBackgroundResource(R.drawable.rounded_corners_darker_colors)
                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.gravity = Gravity.START
                holder.chatBubbleUser.layoutParams = params
                holder.usernameOverBubble.gravity = Gravity.LEFT
                holder.usernameOverBubble.text = it[position].name

            }
            holder.chatBubbleUser.text = it[position].message
        }
    }

    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatBubbleUser: TextView = itemView.findViewById(R.id.chatBubble)
        val usernameOverBubble: TextView = itemView.findViewById(R.id.username)
    }
}





