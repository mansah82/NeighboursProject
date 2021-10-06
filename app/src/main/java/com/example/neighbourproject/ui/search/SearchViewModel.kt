package com.example.neighbourproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neighbourproject.neighbour.NeighboursRepository
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.Neighbour

class SearchViewModel : ViewModel() {

    private val repository : NeighboursService = NeighboursRepository()

    private val search : MutableLiveData<List<Neighbour>> = MutableLiveData()
    val searchResult : LiveData<List<Neighbour>> = search

    fun searchAge(minAge: Int, maxAge: Int){
        search.value = repository.getNeighboursByAge(minAge, maxAge)
    }

    fun searchId(id: String): Neighbour?{
        return repository.getNeighbourById(id)
    }
}