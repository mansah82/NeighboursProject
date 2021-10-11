package com.example.neighbourproject.ui.search

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
import com.example.neighbourproject.neighbour.data.People

class SearchRecyclerAdapter(
    private val neighboursSearch: LiveData<List<People>>,
    lifecycleOwner: LifecycleOwner,
    private val clickListener: ClickListener) :
    RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    companion object{
        private const val TAG = "SearchRecyclerAdapter"
    }

    @SuppressLint("NotifyDataSetChanged")
    private val searchResultObserver = Observer<List<People>> {
        it?.let {
            notifyDataSetChanged()
        }
    }

    init {
        neighboursSearch.observe(lifecycleOwner, searchResultObserver)
    }
    class ViewHolder(view: View, private val listener: ClickListener) : RecyclerView.ViewHolder(view) {
        val neighbourNameTextView: TextView = view.findViewById(R.id.neighbour_name)
        val neighbourAgeTextView: TextView = view.findViewById(R.id.neighbour_age)
        val neighbourGenderTextView: TextView = view.findViewById(R.id.neighbour_gender)
        val neighbourInfoTextView: TextView = view.findViewById(R.id.neighbour_info)
        private lateinit var people: People

        fun bind(newPeople: People){
            people = newPeople

            itemView.setOnClickListener {
                listener.onClick(people.id)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_search_list, viewGroup, false)
        return ViewHolder(view, clickListener)
    }

    private fun infoString(people: People): String {
        var doing = ""
        for(interest in people.interests){
            doing += interest.name.plus(" in ")
                .plus(interest.location?.area?:"-").plus("\n")
        }
        return doing
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        neighboursSearch.value?.let {
            viewHolder.neighbourNameTextView.text = it[position].firstName.plus(" ").plus(it[position].lastName)
            viewHolder.neighbourAgeTextView.text = it[position].age.toString()
            viewHolder.neighbourGenderTextView.text = it[position].gender.text
            viewHolder.neighbourInfoTextView.text = infoString(it[position])

            viewHolder.bind(it[position])
        }
    }
    override fun getItemCount() = neighboursSearch.value?.let { it.size }?: 0
}