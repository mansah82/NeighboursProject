package com.example.neighbourproject.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbourproject.R
import com.example.neighbourproject.neighbour.data.Neighbour

class SearchRecyclerAdapter(
    private val neighboursSearch: LiveData<List<Neighbour>>,
    lifecycleOwner: LifecycleOwner,
    private val clickListener: ClickListener) :
    RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    companion object{
        private const val TAG = "SearchRecyclerAdapter"
    }

    private val searchResultObserver = Observer<List<Neighbour>> {
        it?.let {
            //TODO A hard and not efficient way to tell recyclerview something have changed
            notifyDataSetChanged()
        }
    }

    init {
        neighboursSearch.observe(lifecycleOwner, searchResultObserver)
    }
    class ViewHolder(view: View, private val listener: ClickListener) : RecyclerView.ViewHolder(view) {
        val neighbourNameTextView: TextView = view.findViewById(R.id.neighbour_name)
        val neighbourInfoTextView: TextView = view.findViewById(R.id.neighbour_info)
        private lateinit var neighbour: Neighbour

        fun bind(newNeighbour: Neighbour){
            Log.d(TAG, "Binding ")
            neighbour = newNeighbour

            itemView.setOnClickListener {
                listener.onClick(neighbour.id)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_search_list, viewGroup, false)
        return ViewHolder(view, clickListener)
    }

    private fun infoString(neighbour: Neighbour): String {
        val info = "Age: ".plus(neighbour.age.toString()).plus("\n")
            .plus("Gender: ").plus(neighbour.gender.text).plus("\n")

        var doing = ""
        for(interest in neighbour.getInterests()){
            //TODO OMG, please don't laugh
            doing += interest.name.plus(" in ")
                .plus(interest.location).plus("\n")
        }
        return info + doing
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        neighboursSearch.value?.let {
            viewHolder.neighbourNameTextView.text = it[position].firstName.plus(" ").plus(it[position].lastName)
            viewHolder.neighbourInfoTextView.text = infoString(it[position])

            viewHolder.bind(it[position])
        }
    }

    override fun getItemCount() = neighboursSearch.value?.let { it.size }?: 0
}