package com.example.neighbourproject.ui.chat

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.FriendStatus
import com.example.neighbourproject.neighbour.data.People


class NewMessageActivity : AppCompatActivity() {
    //Koppla med firebase samt endast visa de man är kompis med
    var people = mutableListOf<People>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.show()
        supportActionBar?.title = "Select User"
        var recyclerview_newmessage = findViewById<RecyclerView>(R.id.recyclerview_newmessage)
        recyclerview_newmessage.layoutManager = LinearLayoutManager(this)
        val adapter = MessageAdapter(this, people)
        recyclerview_newmessage.adapter = adapter




    }

    }
