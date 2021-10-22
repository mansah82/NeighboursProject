package com.example.neighbourproject.ui.edit

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.Interest

class InterestAddAdapter( val interestList:ArrayList<Interest>): RecyclerView.Adapter<InterestAddAdapter.InterestViewHolder>() {

    inner class InterestViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.interestTextView)
        val location: TextView = view.findViewById(R.id.locationTextView)
        val remove : ImageView = view.findViewById(R.id.deleteInterestImage)
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.fragment_interest_profile,parent,false)
        Log.d("!!!", "onCreateViewHolder: 1")
        return InterestViewHolder(v)

    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val itemPosition = interestList[position]
        holder.name.text = itemPosition.name
        holder.location.text = itemPosition.location.toString()
        Log.d("!!!", "onBindViewHolder: ")

        holder.remove.setOnClickListener {
            interestList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,interestList.size)
        }
    }

    override fun getItemCount(): Int {
        return  interestList.size
    }
}
