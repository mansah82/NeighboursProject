package com.example.neighbourproject.ui.chat

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.ui.search.SearchRecyclerAdapter


class MessageAdapter(val context : Context, val people: List<People>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>(){
    val TAG = "MessageAdapter"
    val layoutInflater = LayoutInflater.from(context)



    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${people.size}")
        return people.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageAdapter.ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_list_message, parent, false)
        Log.d(TAG, "OncreateViewHolder: ")
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageAdapter.ViewHolder, position: Int) {
        val person = people[position]
        Log.d(TAG, "onBindViewHolder: ")
        holder.nameTextView.text = person.firstName
        //TODO Get picture in recyclerview
        holder.pictureImageView.setImageResource(R.drawable.duck)
        holder.personPosition = position
    }


    inner class ViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val pictureImageView = itemView.findViewById<ImageView>(R.id.profileimageView)
        var personPosition = 0

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, ChatLogActivity::class.java)
                intent.putExtra("id",people[personPosition].id)
                context.startActivity(intent)

            }
        }



    }


    }