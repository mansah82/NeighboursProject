package com.example.neighbourproject.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.databinding.SearchFragmentBinding
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.ui.location.NeighbourActivity

class SearchFragment : Fragment(), ClickListener {
    companion object{
        private const val TAG = "SearchFragment"
        private const val DEFAULT_MIN_AGE = 0
        private const val DEFAULT_MAX_AGE = 140
    }
    private lateinit var binding: SearchFragmentBinding

    private val model : SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(layoutInflater)

        val searchAdapter = SearchRecyclerAdapter(model.searchResult,
            viewLifecycleOwner,
            this as ClickListener)

        binding.searchResultList.adapter = searchAdapter

        binding.chipFemale.text = Gender.FEMALE.text
        binding.chipMale.text = Gender.MALE.text
        binding.chipEnby.text = Gender.ENBY.text
        binding.chipNone.text = Gender.NONE.text

        return binding.root
    }

    private var minAge = DEFAULT_MIN_AGE
    private var maxAge = DEFAULT_MAX_AGE
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipFemale.setOnClickListener {
            doSearch()
        }
        binding.chipMale.setOnClickListener {
            doSearch()
        }
        binding.chipEnby.setOnClickListener {
            doSearch()
        }
        binding.chipNone.setOnClickListener {
            doSearch()
        }

        binding.minAge.doAfterTextChanged {
            minAge = try {
                binding.minAge.text.toString().toInt()
            }catch (e : NumberFormatException){
                DEFAULT_MIN_AGE
            }
            doSearch()
        }

        binding.maxAge.doAfterTextChanged {
            maxAge = try {
                binding.maxAge.text.toString().toInt()
            }catch (e : NumberFormatException){
                DEFAULT_MAX_AGE
            }
            doSearch()
        }

        binding.freeSearchText.doAfterTextChanged {
            doSearch()
        }
    }

    private fun selectedGenders(): List<Gender>{
        val result = mutableListOf<Gender>()
        if(binding.chipFemale.isChecked)
            result.add(Gender.FEMALE)
        if(binding.chipMale.isChecked)
            result.add(Gender.MALE)
        if(binding.chipEnby.isChecked)
            result.add(Gender.ENBY)
        if(binding.chipNone.isChecked)
            result.add(Gender.NONE)

        return result
    }

    private fun doSearch(){
        model.search(minAge, maxAge, selectedGenders(), binding.freeSearchText.text.toString())
    }

    override fun onClick(id: String) {
        val neighbour = model.searchId(id)
        neighbour?.let{
            //TODO intent for send friend request or detailed view go's here
            Toast
                .makeText(context,  "You Like: ".plus(neighbour.toString()), Toast.LENGTH_LONG)
                .show()

            startActivity(Intent(requireContext(), NeighbourActivity::class.java))
        }
    }
}