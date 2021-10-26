package com.example.neighbourproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.SearchParameters
import com.example.neighbourproject.neighbour.data.People
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {

    private val repository: NeighboursService by inject()

    fun searchId(id: String): People? {
        return repository.getNeighbourById(id)
    }

    fun getSearchObserver(): LiveData<List<People>> {
        return repository.searchResultUpdate
    }

    fun setSearch(searchParameters: SearchParameters) {
        repository.setSearch(searchParameters)
    }
}