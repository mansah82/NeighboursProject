package com.example.neighbourproject.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R

class ChatLogActivity : AppCompatActivity() {
    private val model: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        supportActionBar?.title = "Chat"

        var recyclerviewChatLog = findViewById<RecyclerView>(R.id.recyclerView_chat_log)
        recyclerviewChatLog.adapter = ChatLogAdapter(this, model, this)

        val sendButton = findViewById<Button>(R.id.send_button_chat_log)
        val textMessage = findViewById<TextView>(R.id.enter_message_chat_log)

        sendButton.setOnClickListener {
            model.writeMessage(textMessage.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        model.startChat()
    }

    override fun onPause() {
        super.onPause()
        model.stopChat()
    }
}