package com.example.neighbourproject.ui.location

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.databinding.NeighbourFragmentBinding

class NeighbourFragment : Fragment() {
    companion object {
        private const val TAG = "NeighbourFragment"
    }

    private lateinit var binding: NeighbourFragmentBinding

    private val model: NeighbourViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NeighbourFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getNeighbour()?.let {
            Log.d(TAG, "Got a neighbour to show in UI")

            binding.neighbourName.text = it.firstName.plus(" ").plus(it.lastName)

            var doing = "Age: ".plus(it.age.toString()).plus("\n")
            for(interest in it.getInterests()){
                //TODO refactor, same function in search recycler view
                doing += interest.name.plus(" in ")
                    .plus(interest.location.area).plus("\n")
            }
            binding.neighbourInterests.setText(doing)

            binding.location.text =
        }



    }




}