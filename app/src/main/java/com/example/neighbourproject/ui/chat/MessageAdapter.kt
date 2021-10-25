package com.example.neighbourproject.ui.chat

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.ui.search.SearchRecyclerAdapter

class MessageAdapter(val context : Context, val people: List<People>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    val layoutInflater = LayoutInflater.from(context)


    override fun getItemCount(): Int {

        return people.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_list_message, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        val person = people[position]

        holder.nameTextView.text = person.firstName
        TODO("Få in bild i recyclerviewen")
        //holder.pictureImageView.toString() = person.image

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val pictureImageView = itemView.findViewById<ImageView>(R.id.profileimageView)
    }


    }