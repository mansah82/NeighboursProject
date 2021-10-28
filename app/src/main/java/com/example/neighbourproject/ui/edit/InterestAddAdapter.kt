package com.example.neighbourproject.ui.edit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.Area
import com.example.neighbourproject.neighbour.data.Interest
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.neighbour.data.Position

class InterestAddAdapter(private val profile: People,  private val model: EditViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TAG = "InterestAddAdapter"
        private const val VIEW_TYPE_FOOTER = 0
        private const val VIEW_TYPE_CELL = 1
    }

    class InterestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val interestName: TextView = view.findViewById(R.id.interestTextView)
        val area: TextView = view.findViewById(R.id.locationTextView)
        val longitude: TextView = view.findViewById(R.id.longitudeTextView)
        val latitude: TextView = view.findViewById(R.id.latitudeTextView)
        val removeButton: ImageView = view.findViewById(R.id.deleteInterestImage)
    }

    class InterestAddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val interestName: TextView = view.findViewById(R.id.interestTextView)
        val area: TextView = view.findViewById(R.id.locationTextView)
        val addButton: ImageView = view.findViewById(R.id.addInterestImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CELL) {
            val inflater = LayoutInflater.from(parent.context)
            val v = inflater.inflate(R.layout.item_interest_profile, parent, false)
            return InterestViewHolder(v)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val v = inflater.inflate(R.layout.item_interest_profile_add, parent, false)
            return InterestAddViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == profile.interests.size) {
            (holder as InterestAddViewHolder).addButton.setOnClickListener {
                if(holder.interestName.text.toString().isNotEmpty()) {
                    Log.d(TAG, "Adding a new thing ${holder.interestName} - ${holder.area}")
                    profile.addInterest(
                        Interest(
                            holder.interestName.text.toString(),
                            Area(holder.area.text.toString(), model.getCurrentPosition())
                        )
                    )
                    notifyItemInserted(position)
                    notifyItemRangeChanged(position, itemCount)
                }
            }
        } else {
            val itemPosition = profile.interests[position]
            (holder as InterestViewHolder).interestName.text = itemPosition.name
            itemPosition.location?.let { area ->
                holder.area.text = area.area
                area.position?.let {
                    holder.latitude.text = it.latitude.toString()
                    holder.longitude.text = it.longitude.toString()
                }
            }

            holder.removeButton.setOnClickListener {
                profile.interests.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)
            }
        }
    }

    override fun getItemCount(): Int {
        return profile.interests.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == profile.interests.size) VIEW_TYPE_FOOTER else VIEW_TYPE_CELL
    }
}
