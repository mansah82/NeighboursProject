package com.example.neighbourproject.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neighbourproject.neighbour.NeighboursService
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.People
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {

    private val repository: NeighboursService by inject()

    private val search: MutableLiveData<List<People>> = MutableLiveData()
    val searchResult: LiveData<List<People>> = search

    fun search(minAge: Int, maxAge: Int, genders: List<Gender>, free_text: String) {
        val ageResult = repository.getNeighboursByAge(minAge, maxAge)
        val genderResult = mutableListOf<People>()
        for (neighbour in ageResult) {
            if (neighbour.gender in genders) {
                genderResult.add(neighbour)
            }
        }
        val freeTextResult = mutableListOf<People>()
        if (free_text != "") {
            for (neighbour in genderResult) {
                if (neighbour.toString().contains(free_text, true)) {
                    freeTextResult.add(neighbour)
                }
            }
        } else {
            freeTextResult.addAll(genderResult)
        }

        search.value = freeTextResult
    }

    fun searchId(id: String): People? {
        return repository.getNeighbourById(id)
    }
}