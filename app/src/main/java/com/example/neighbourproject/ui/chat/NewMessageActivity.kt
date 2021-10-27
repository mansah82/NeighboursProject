package com.example.neighbourproject.ui.chat

import android.content.ClipData
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.FriendStatus
import com.example.neighbourproject.neighbour.data.People
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class NewMessageActivity : AppCompatActivity() {
    //Koppla med firebase samt endast visa de man är kompis med
    private val model: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.show()
        supportActionBar?.title = "Friends"

        var recyclerview_newmessage = findViewById<RecyclerView>(R.id.recyclerview_newmessage)
        val adapter = MessageAdapter(this, model.getFriends())
        recyclerview_newmessage.adapter = adapter










    }
}

