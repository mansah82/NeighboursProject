package com.example.neighbourproject.ui.chat

import android.R.attr
import android.annotation.SuppressLint
import android.content.Context
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
import android.R.attr.button

import android.widget.LinearLayout


class ChatLogAdapter(
    private val context: Context,
    private val model: ChatViewModel,
    lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ChatLogAdapter.ViewHolder>() {
    val TAG = "ChatLogAdapter"
    val layoutInflater = LayoutInflater.from(context)


    @SuppressLint("NotifyDataSetChanged")
    private val chatObserver = Observer<List<ChatMessage>> {
        it?.let {
            notifyDataSetChanged()
        }
    }

    init {
        model.getLiveMessages().observe(lifecycleOwner, chatObserver)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${model.getLiveMessages().value?.size}")
        return model.getLiveMessages().value?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatLogAdapter.ViewHolder {
        val itemViewUser = layoutInflater.inflate(R.layout.chat_bubble_list_chat_log, parent, false)
        Log.d(TAG, "OncreateViewHolder: ")
        return ViewHolder(itemViewUser)
    }

    override fun onBindViewHolder(holder: ChatLogAdapter.ViewHolder, position: Int) {
        model.getLiveMessages().value?.let {
            holder.chatBubbleUser.text = it[position].message
            Log.d(TAG, "onBindViewHolder: ")
            if (it[position].id == model.getId()) {
                holder.chatBubbleUser.setBackgroundResource(R.drawable.rounded_edittext_signin)

                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.gravity = Gravity.END
                holder.chatBubbleUser.layoutParams = params

            } else {
                holder.chatBubbleUser.setBackgroundResource(R.drawable.rounded_corners_darker_colors)
                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.gravity = Gravity.START
                holder.chatBubbleUser.layoutParams = params
            }
        }
    }

    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chatBubbleUser = itemView.findViewById<TextView>(R.id.chatBubble)
    }
}





