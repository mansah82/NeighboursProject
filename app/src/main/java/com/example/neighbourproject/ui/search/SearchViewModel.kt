package com.example.neighbourproject.ui.search

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.neighbourproject.location.LocationService
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.SearchParameters
import com.example.neighbourproject.neighbour.data.People
import com.example.neighbourproject.neighbour.data.Position
import com.example.neighbourproject.storage.StorageService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {

    private val neighboursService: NeighboursService by inject()
    private val locationService: LocationService by inject()
    private val storageService: StorageService by inject()

    // TODO this is wrong, ActivityViewModel ???
    private var context: Context? = null
    fun setContext(cont: Context) {
        context = cont
    }
    fun loadImage(url: String, view: ImageView){
        context?.let {
            storageService.loadImage(it, url, view)
        }
    }

    fun setLastPosition(position: Position) {
        locationService.setLastPosition(position)
    }

    fun searchId(id: String): People? {
        return neighboursService.getNeighbourById(id)
    }

    fun getSearchObserver(): LiveData<List<People>> {
        return neighboursService.searchResultUpdate
    }

    fun setSearch(searchParameters: SearchParameters) {
        neighboursService.setSearch(searchParameters)
    }
}