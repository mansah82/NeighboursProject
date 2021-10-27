package com.example.neighbourproject.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        supportActionBar?.title = "Chat"

        var recyclerview_chatLog = findViewById<RecyclerView>(R.id.recyclerView_chat_log)
        //val adapter = ChatLogAdapter(this, )
           // recyclerview_chatLog.adapter = adapter

    }
}