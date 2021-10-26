package com.example.neighbourproject.ui.edit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.Interest
import com.example.neighbourproject.neighbour.data.People

class InterestAddAdapter(private val profile: People): RecyclerView.Adapter<InterestAddAdapter.InterestViewHolder>() {
    companion object {
        private const val TAG = "InterestAddAdapter"
    }

    inner class InterestViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.interestTextView)
        val location: TextView = view.findViewById(R.id.locationTextView)
        val longitude: TextView = view.findViewById(R.id.longitudeTextView)
        val latitude: TextView = view.findViewById(R.id.latitudeTextView)
        val removeButton : ImageView = view.findViewById(R.id.deleteInterestImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_interest_profile,parent,false)
        Log.d(TAG, "onCreateViewHolder: 1")
        return InterestViewHolder(v)
    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val itemPosition = profile.interests[position]
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
            profile.interests.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,profile.interests.size)
        }
    }

    override fun getItemCount(): Int {
        return profile.interests.size
    }
}
