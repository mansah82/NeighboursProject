package com.example.neighbourproject.ui.neigbour

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
            binding.neighbourAge.text =  it.age.toString()
            binding.neighbourGender.text = it.gender.text
            binding.neighbourStatus.text = it.relationshipStatus.text

            var doing = ""
            for(interest in it.interests){
                //TODO refactor, put into a matrix or something
                doing += interest.name.plus(" in ")
                val location = interest.location
                if(location != null) {
                    doing += location.area.plus(" ")
                    val position = location.position
                    doing += if(position != null) {
                        model.calculateDistanceToMe(position).plus("\n")
                    }else{
                        "\n"
                    }
                }else {
                    doing += "-\n"
                }
            }
            binding.neighbourInterests.setText(doing)
        }
    }
}