package com.example.neighbourproject.ui.search


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {
    companion object{
        private const val TAG = "SearchFragment"
    }
    private lateinit var binding: SearchFragmentBinding

    private val model : SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(layoutInflater)

        val searchAdapter = SearchRecyclerAdapter(model.searchResult, viewLifecycleOwner)
        binding.searchResultList.adapter = searchAdapter

        return binding.root
    }

    private var minAge = 0
    private var maxAge = 140
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        binding.minAge.doAfterTextChanged {
            minAge = binding.minAge.text.toString().toInt()
            Log.d(TAG, "doAfterTextChanged min: $minAge")
            model.searchAge(minAge, maxAge)
        }

        binding.maxAge.doAfterTextChanged {
            maxAge = binding.maxAge.text.toString().toInt()
            Log.d(TAG, "doAfterTextChanged max: $maxAge")
            model.searchAge(minAge, maxAge)
        }

    }


}