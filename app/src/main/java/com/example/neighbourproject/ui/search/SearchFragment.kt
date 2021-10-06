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
        private const val DEFAULT_MIN_AGE = 0
        private const val DEFAULT_MAX_AGE = 140
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

    private var minAge = DEFAULT_MIN_AGE
    private var maxAge = DEFAULT_MAX_AGE
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        binding.minAge.doAfterTextChanged {
            minAge = try {
                binding.minAge.text.toString().toInt()
            }catch (e : NumberFormatException){
                DEFAULT_MIN_AGE
            }

            Log.d(TAG, "doAfterTextChanged min: $minAge")
            model.searchAge(minAge, maxAge)
        }

        binding.maxAge.doAfterTextChanged {
            maxAge = try {
                binding.maxAge.text.toString().toInt()
            }catch (e : NumberFormatException){
                DEFAULT_MAX_AGE
            }

            Log.d(TAG, "doAfterTextChanged max: $maxAge")
            model.searchAge(minAge, maxAge)
        }
    }
}