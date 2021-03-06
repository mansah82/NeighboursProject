package com.example.neighbourproject.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.ui.edit.EditProfileActivity
import com.example.neighbourproject.databinding.SearchFragmentBinding
import com.example.neighbourproject.neighbour.SearchParameters
import com.example.neighbourproject.neighbour.data.Gender
import com.example.neighbourproject.neighbour.data.RelationshipStatus
import com.example.neighbourproject.ui.chat.ChatLogActivity
import com.example.neighbourproject.ui.neigbour.ExtrasKey
import com.example.neighbourproject.ui.neigbour.NeighbourActivity

class SearchFragment : Fragment(), ClickListener {
    companion object {
        private const val TAG = "SearchFragment"
    }

    private lateinit var binding: SearchFragmentBinding

    private val model: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(layoutInflater)

        model.setContext(requireContext())

        val searchAdapter = SearchRecyclerAdapter(
            model.getSearchObserver(),
            viewLifecycleOwner,
            this as ClickListener,
            model
        )

        binding.searchResultList.adapter = searchAdapter
        return binding.root
    }

    private var minAge = SearchParameters.DEFAULT_MIN_AGE
    private var maxAge = SearchParameters.DEFAULT_MAX_AGE
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.femaleButton.setOnClickListener {
            doSearch()
        }
        binding.maleButton.setOnClickListener {
            doSearch()
        }
        binding.nonBinButton.setOnClickListener {
            doSearch()
        }

        binding.minAge.doAfterTextChanged {
            minAge = try {
                binding.minAge.text.toString().toInt()
            } catch (e: NumberFormatException) {
                SearchParameters.DEFAULT_MIN_AGE
            }
            doSearch()
        }

        binding.maxAge.doAfterTextChanged {
            maxAge = try {
                binding.maxAge.text.toString().toInt()
            } catch (e: NumberFormatException) {
                SearchParameters.DEFAULT_MAX_AGE
            }
            doSearch()
        }

        binding.freeSearchText.doAfterTextChanged {
            doSearch()
        }

        binding.buttonEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        doSearch()

        binding.chattButton.setOnClickListener {
            startActivity(Intent(requireContext(), ChatLogActivity::class.java))
        }

        binding.backArrow.setOnClickListener {
            activity?.onBackPressed()

        }

    }

    override fun onResume() {
        super.onResume()
        doSearch()
    }

    private fun selectedGenders(): List<Gender> {
        val result = mutableListOf<Gender>()
        if (binding.femaleButton.isChecked)
            result.add(Gender.FEMALE)
        if (binding.maleButton.isChecked)
            result.add(Gender.MALE)
        if (binding.nonBinButton.isChecked) {
            result.add(Gender.ENBY)
            result.add(Gender.NONE)
        }
        return result
    }

    private fun selectedRelationship(): List<RelationshipStatus> {
        val result = mutableListOf<RelationshipStatus>()
        result.add(RelationshipStatus.SINGLE)
        result.add(RelationshipStatus.NONE)
        result.add(RelationshipStatus.DIVORCE)
        result.add(RelationshipStatus.MARRIED)
        result.add(RelationshipStatus.RELATIONSHIP)
        return result
    }

    private fun doSearch() {
        val params = SearchParameters(
            minAge,
            maxAge,
            selectedGenders(),
            selectedRelationship(),
            binding.freeSearchText.text.toString()
        )
        model.setSearch(params)
    }

    override fun onClick(id: String) {
        val neighbour = model.searchId(id)
        neighbour?.let {

            val intent = Intent(requireContext(), NeighbourActivity::class.java).also {
                it.putExtra(ExtrasKey.KEY_USER_ID, neighbour.id)
            }
            startActivity(intent)
        }
    }
}