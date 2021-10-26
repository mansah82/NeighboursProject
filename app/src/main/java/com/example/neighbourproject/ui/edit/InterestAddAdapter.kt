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

class InterestAddAdapter(private val interestList:MutableList<Interest>): RecyclerView.Adapter<InterestAddAdapter.InterestViewHolder>() {
    val TAG = "InterestAddAdapter"
    inner class InterestViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.interestTextView)
        val location: TextView = view.findViewById(R.id.locationTextView)
        val longitude: TextView = view.findViewById(R.id.longitudeTextView)
        val latitude: TextView = view.findViewById(R.id.latitudeTextView)
        val removeButton : ImageView = view.findViewById(R.id.deleteInterestImage)
    }

    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_interest_profile,parent,false)
        Log.d(TAG, "onCreateViewHolder: 1")
        return InterestViewHolder(v)

    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val itemPosition = interestList[position]
        holder.name.text = itemPosition.name
        itemPosition.location?.let {area ->
            holder.location.text = area.area
            area.position?.let {
                holder.latitude.text = it.latitude.toString()
                holder.longitude.text = it.longitude.toString()
            }

        }

        Log.d(TAG, "onBindViewHolder: ")

        holder.removeButton.setOnClickListener {
            interestList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,interestList.size)



        }
    }

    override fun getItemCount(): Int {
        return  interestList.size
    }


}
