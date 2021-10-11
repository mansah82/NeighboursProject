package com.example.neighbourproject.ui.neigbour

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.Interest
import com.example.neighbourproject.neighbour.data.People

class InterestRecyclerAdapter(
    private val interestList: List<Interest>,
    private val clickListener: InterestClickListener,
    private val model: NeighbourViewModel) :
    RecyclerView.Adapter<InterestRecyclerAdapter.ViewHolder>() {

    companion object{
        private const val TAG = "InterestRecyclerAdapter"
    }

    class ViewHolder(view: View, private val listener: InterestClickListener) : RecyclerView.ViewHolder(view) {
        val interestName: TextView = view.findViewById(R.id.interest_name)
        val interestArea: TextView = view.findViewById(R.id.interest_area)
        val interestDistance: TextView = view.findViewById(R.id.interest_distance)
        private lateinit var interest: Interest

        fun bind(newInterest: Interest){
            interest = newInterest

            itemView.setOnClickListener {
                interest.location?.let {
                    listener.onClick(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_interest_list, viewGroup, false)
        return ViewHolder(view, clickListener)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.interestName.text = interestList[position].name
        val location = interestList[position].location
        if(location != null) {
            viewHolder.interestArea.text = location.area
            val pos = location.position
            if (pos != null) {
                viewHolder.interestDistance.text = model.calculateDistanceToMe(pos)
            }else{
                viewHolder.interestDistance.text = "? km"
            }
        }else{
            viewHolder.interestArea.text = "-"
            viewHolder.interestDistance.text = "? km"
        }

        viewHolder.bind(interestList[position])

    }
    override fun getItemCount() = interestList.size
}