package com.example.neighbourproject.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.chat.data.ChatMessage

class ChatLogActivity : AppCompatActivity() {
    private val model: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        supportActionBar?.title = "Chat"

        var recyclerview_chatLog = findViewById<RecyclerView>(R.id.recyclerView_chat_log)
        val adapter = ChatLogAdapter(this, model, this)
            recyclerview_chatLog.adapter = adapter

        val sendButton = findViewById<Button>(R.id.send_button_chat_log)
        val textMessage = findViewById<TextView>(R.id.enter_message_chat_log)

        sendButton.setOnClickListener {
            model.writeMessage(textMessage.text.toString())
        }

    }
}