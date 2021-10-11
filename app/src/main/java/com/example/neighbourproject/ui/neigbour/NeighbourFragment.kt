package com.example.neighbourproject.ui.neigbour

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.neighbourproject.databinding.NeighbourFragmentBinding
import com.google.protobuf.DescriptorProtos
import java.lang.Exception

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

    private fun calcDistance(lat1: Double?, lon1: Double?, lat2: String?, lon2: String?): String{
        Log.d(TAG, "Location: ")
        return if(lat1 != null && lon1 != null && lat2 != null && lon2 != null ){
            try {
                val results = FloatArray(1)
                Location.distanceBetween(
                    lat1, lon1,
                    lat2.toDouble(), lon2.toDouble(),
                    results)
                "- ".plus("${results[0]} meters")
            }catch (e : NumberFormatException){
                Log.d(TAG, "Failed on getting distance -  NumberFormatException")
                "- No dist\n"
            }

        }else {
            "- No dist\n"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getNeighbour()?.let {
            Log.d(TAG, "Got a neighbour to show in UI")

            binding.neighbourName.text = it.firstName.plus(" ").plus(it.lastName)
            binding.neighbourAge.text =  it.age.toString()

            var doing = ""
            for(interest in it.interests){
                //TODO refactor, same function in search recycler view
                doing += interest.name.plus(" in ")
                if(interest.location != null) {
                    Log.d(TAG, "Location is not null")
                    doing += interest.location.area.plus(" ")
                    doing += calcDistance(
                        model.getLocation()?.latitude,
                        model.getLocation()?.longitude,
                        interest.location?.lat,
                        interest.location?.lon
                    )

                }else {
                    doing += "-\n"
                }


            }
            binding.neighbourInterests.setText(doing)




            /*if(it. .area.location == null) {
                binding.neighbourDistance.text = it.area.area
            }else{
                model.getLocation()?.let { location ->
                    binding.neighbourDistance.text = location.distanceTo(it.area.location).toString()
                }
            }*/
        }
    }
}